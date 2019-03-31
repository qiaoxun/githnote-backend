package com.study.Utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class GitUtils {

    private static Logger logger = LoggerFactory.getLogger("com.study.Utils.GitUtils");

    private static final String allChangedFilesPattern = ".";

    private Git git;

    /**
     * get a git instance from the path of repository
     * @param repositoryPath
     * @return
     * @throws IOException
     */
    public static Git getGit(String repositoryPath) throws IOException {
        return getGit(new File(repositoryPath));
    }

    /**
     * get a git instance from repository
     * @param repositoryFile
     * @return
     * @throws IOException
     */
    public static Git getGit(File repositoryFile) throws IOException {
        return Git.open(repositoryFile);
    }

    public GitUtils(String repositoryPath) {
        try {
            this.git = getGit(new File(repositoryPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GitUtils(Git git) {
        this.git = git;
    }

    /**
     * add file
     * @param relativePath
     */
    public GitUtils addFile(String relativePath) throws GitAPIException{
        try {
            this.git.add().addFilepattern(relativePath).call();
        } catch (NoFilepatternException e) {
            logger.error("NoFilepatternException", e);
            throw e;
        } catch (GitAPIException e) {
            logger.error("GitAPIException", e);
            throw e;
        }
        return this;
    }

    /**
     * add all changed files
     * @throws GitAPIException
     */
    public GitUtils addAllChangedFiles() throws GitAPIException {
        addFile(allChangedFilesPattern);
        return this;
    }


    /**
     * commit changes
     * @param message
     * @throws GitAPIException
     */
    public GitUtils commit(String message) throws GitAPIException {
        try {
            this.git.commit().setMessage(message).call();
        } catch (GitAPIException e) {
            logger.error("GitAPIException", e);
            throw e;
        }
        return this;
    }

    /**
     * add file and then commit
     * @param relativePath
     * @param message
     * @throws GitAPIException
     */
    public GitUtils addAndCommit(String relativePath, String message) throws GitAPIException {
        addFile(relativePath);
        commit(message);
        return this;
    }

    /**
     * add all changed files and then commit
     * @param message
     * @throws GitAPIException
     */
    public GitUtils addAllChangedFilesAndCommit(String message) throws GitAPIException {
        addAllChangedFiles();
        commit(message);
        return this;
    }

    /**
     * push to remote repository
     * @throws GitAPIException
     */
    public GitUtils push() throws GitAPIException {
        this.git.push().call();
        return this;
    }

    /**
     * pull from remote repository
     * @return
     * @throws GitAPIException
     */
    public GitUtils pull() throws GitAPIException {
        this.git.pull().call();
        return this;
    }
}
