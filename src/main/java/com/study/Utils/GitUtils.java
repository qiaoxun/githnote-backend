package com.study.Utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class GitUtils {

    private static Logger logger = LoggerFactory.getLogger("com.study.Utils.GitUtils");

    private static final String allChangedFilesPattern = ".";

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

    /**
     * add file
     * @param repository
     * @param relativePath
     */
    public static void addFile(Repository repository, String relativePath) throws GitAPIException {
        try {
            Git git = new Git(repository);
            git.add().addFilepattern(relativePath).call();
        } catch (NoFilepatternException e) {
            logger.error("NoFilepatternException", e);
            throw e;
        } catch (GitAPIException e) {
            logger.error("GitAPIException", e);
            throw e;
        }
    }


    /**
     * add file
     * @param git
     * @param relativePath
     */
    public static void addFile(Git git, String relativePath) throws GitAPIException{
        try {
            git.add().addFilepattern(relativePath).call();
        } catch (NoFilepatternException e) {
            logger.error("NoFilepatternException", e);
            throw e;
        } catch (GitAPIException e) {
            logger.error("GitAPIException", e);
            throw e;
        }
    }

    /**
     * add all changed files
     * @param git
     * @throws GitAPIException
     */
    public static void addAllChangedFiles(Git git) throws GitAPIException {
        addFile(git, allChangedFilesPattern);
    }


    /**
     * commit changes
     * @param git
     * @param message
     * @throws GitAPIException
     */
    public static void commit(Git git, String message) throws GitAPIException {
        try {
            git.commit().setMessage(message).call();
        } catch (GitAPIException e) {
            logger.error("GitAPIException", e);
            throw e;
        }
    }

    /**
     * add file and then commit
     * @param git
     * @param relativePath
     * @param message
     * @throws GitAPIException
     */
    public static void addAndCommit(Git git, String relativePath, String message) throws GitAPIException {
        addFile(git, relativePath);
        commit(git, message);
    }

    /**
     * add all changed files and then commit
     * @param git
     * @param message
     * @throws GitAPIException
     */
    public static void addAllChangedFilesAndCommit(Git git, String message) throws GitAPIException {
        addAllChangedFiles(git);
        commit(git, message);
    }

    /**
     * push to remote repository
     * @param git
     * @throws GitAPIException
     */
    public static void push(Git git) throws GitAPIException {
        git.push().call();
    }
}
