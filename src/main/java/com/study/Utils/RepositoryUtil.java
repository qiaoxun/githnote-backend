package com.study.Utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class RepositoryUtil {
    private static Logger logger = LoggerFactory.getLogger("com.study.Utils.RepositoryUtil");

    /**
     * clone repository from remote url
     * @param remoteUrl the remote repository url like git@github.xx:xxx/xxxx.git
     * @param localPath clone remote repository to local
     * @return false means clone failed
     */
    public static boolean cloneFromRemoteResp(String remoteUrl, String localPath) {
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        try(Git result = Git.cloneRepository().setURI(remoteUrl).setDirectory(localFile).call()) {
            return true;
        } catch (InvalidRemoteException e) {
            logger.error("InvalidRemoteException", e);
        } catch (TransportException e) {
            logger.error("TransportException", e);
        } catch (GitAPIException e) {
            logger.error("GitAPIException", e);
        }
        return false;
    }

    /**
     * open a local repository, the path must contains .git folder
     * @param repoDir
     * @return
     */
    public static Repository openLocalRepository(String repoDir) {
        return openLocalRepository(new File(repoDir));
    }

    /**
     * open a local repository, the path must contains .git folder
     * @param repoDir
     * @return
     */
    public static Repository openLocalRepository(File repoDir) {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try {
            Repository repository = builder.setGitDir(repoDir)
                    .readEnvironment()
                    .findGitDir()
                    .build();
            return repository;
        } catch (IOException e) {
            logger.error("IOException", e);
        }
        return null;
    }

    /**
     * Create a new local repository
     * @param repoPath the path of local repository
     * @return Repository
     */
    public static Repository createNewLocalRepository(String repoPath) {
        File repoFile = new File(repoPath);
        if (!repoFile.exists()) {
            repoFile.mkdirs();
        }
        try {
            Git git = Git.init().setDirectory(repoFile).call();
            return git.getRepository();
        } catch (GitAPIException e) {
            logger.error("IOException", e);
        }
        return null;
    }

}
