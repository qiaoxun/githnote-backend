package com.study;

import static org.junit.Assert.assertTrue;

import com.study.Utils.GitUtils;
import com.study.Utils.RepositoryUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void cloneFormRemote() {
        String remoteUrl = "git@github.com:woai3c/vue-generate-form.git";
        File localPath = new File("C:\\Users\\qiaox.CORPDOM\\Desktop\\jGitTest\\vue_generate_form");
        if (!localPath.exists()) {
            localPath.mkdirs();
        }

        System.out.println("Cloning remote url " + remoteUrl + " to local path" + localPath.toString());

        try(Git result = Git.cloneRepository().setURI(remoteUrl).setDirectory(localPath).call()) {
            System.out.println("Having repository: " + result.getRepository().getDirectory());
        } catch (InvalidRemoteException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRepository() {
        try {
            Repository repository = new FileRepository("C:\\Users\\qiaox.CORPDOM\\Desktop\\jGitTest\\vue_generate_form");
            Git git = new Git(repository);
            git.lsRemote().call().forEach(System.out::println);
            System.out.println("======");
            git.lsRemote().setTags(true).call().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRemoteException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public Repository openLocalRepository() {
        String localPath = "C:\\Users\\qiaox.CORPDOM\\Desktop\\jGitTest\\vue_generate_form";
        File repoDir = new File(localPath);
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try {
            Repository repository = builder.setGitDir(repoDir).readEnvironment().findGitDir().build();
            System.out.println("Having repository: " + repository.getDirectory());

            // the Ref holds an ObjectId for any type of object (tree, commit, blob, tree)
            Ref head = repository.exactRef("refs/heads/master");
            System.out.println("Ref of refs/heads/master: " + head);
            return repository;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void openLocalRepository2() {
        try {
            Repository repository = new FileRepository("C:\\Users\\qiaox.CORPDOM\\Desktop\\jGitTest\\vue_generate_form");
            System.out.println("Having repository: " + repository.getDirectory());
            // the Ref holds an ObjectId for any type of object (tree, commit, blob, tree)
            Ref head = repository.exactRef("refs/heads/master");
            System.out.println("Ref of refs/heads/master: " + head);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createNewRepo() {
        Repository repository = RepositoryUtil.createNewLocalRepository("C:\\Users\\qiaox.CORPDOM\\Desktop\\jGitTest\\vue_generate_form2");
        System.out.println(repository.getDirectory());
    }

    @Test
    public void addFile() throws GitAPIException {
        Repository repository = RepositoryUtil.openLocalRepository("C:\\Users\\qiaox.CORPDOM\\Desktop\\jGitTest\\vue_generate_form2\\.git");
        System.out.println("Having repository: " + repository.getDirectory());
        Ref head = null;
        try {
            head = repository.exactRef("refs/heads/master");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Ref of refs/heads/master: " + head);
        GitUtils.addFile(repository, "aaa.txt");
    }

    @Test
    public void addFile2() throws GitAPIException {
        Git git = null;
        try {
            git = GitUtils.getGit("C:\\Users\\qiaox.CORPDOM\\Desktop\\jGitTest\\vue_generate_form2");
        } catch (IOException e) {
            e.printStackTrace();
        }
        GitUtils.addFile(git, "aaaa.txt");
    }

    @Test
    public void commitFiles() {
        try {
            Git git = GitUtils.getGit("C:\\Users\\qiaox.CORPDOM\\Desktop\\jGitTest\\vue_generate_form2");
            GitUtils.commit(git, "commit test");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addAndCommit() throws IOException {
        Git git = GitUtils.getGit("C:\\Users\\qiaox.CORPDOM\\Desktop\\jGitTest\\vue_generate_form2");
        try {
            GitUtils.addAndCommit(git, "bbb.txt", "add and commit test");
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addAllAndCommit() throws IOException, GitAPIException {
        Git git = GitUtils.getGit("C:\\study\\gitnote\\githnote-backend");
        GitUtils.addAllChangedFilesAndCommit(git, "add some methods");
        GitUtils.push(git);
        git.close();
    }
}
