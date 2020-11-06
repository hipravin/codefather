package com.hipravin;

import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JgitSampleTest {

    public void commit_logs() throws IOException, NoHeadException, GitAPIException {
        List<String> logMessages = new ArrayList<String>();
        Git git = Git.open(Paths.get("C:\\dev\\codefather\\.git").toFile());

        Iterable<RevCommit> log = git.log().call();
        RevCommit previousCommit = null;
        for (RevCommit commit : log) {
            if (previousCommit != null) {
                AbstractTreeIterator oldTreeIterator = getCanonicalTreeParser( previousCommit, git.getRepository() );
                AbstractTreeIterator newTreeIterator = getCanonicalTreeParser( commit, git.getRepository() );
                OutputStream outputStream = new ByteArrayOutputStream();
                try( DiffFormatter formatter = new DiffFormatter( outputStream ) ) {
                    formatter.setRepository( git.getRepository() );
                    formatter.format( oldTreeIterator, newTreeIterator );
                }
                String diff = outputStream.toString();
                System.out.println(diff);
            }
            System.out.println("LogCommit: " + commit);
            String logMessage = commit.getFullMessage();
            System.out.println("LogMessage: " + logMessage);
            logMessages.add(logMessage.trim());
            previousCommit = commit;
        }
        git.close();
    }


    private AbstractTreeIterator getCanonicalTreeParser( ObjectId commitId, Repository repository ) throws IOException {
        try( RevWalk walk = new RevWalk( repository ) ) {
            RevCommit commit = walk.parseCommit( commitId );
            ObjectId treeId = commit.getTree().getId();
            try( ObjectReader reader = repository.newObjectReader() ) {
                return new CanonicalTreeParser( null, reader, treeId );
            }
        }
    }

    @Test
    void testSelf() throws IOException, GitAPIException {
        Git git = Git.open(Paths.get("C:\\dev\\codefather\\.git").toFile());
//        Git git = Git.open(Paths.get("C:\\dev\\tutorials\\spring-boot\\.git").toFile());

        Iterable<RevCommit> log = git.log().call();
        PrintStream out = new PrintStream("gitlog.txt");

        RevCommit previousCommit = null;
        for (RevCommit rc : log) {
            System.out.println(rc.getFullMessage());

            out.println("=========================================================");
            out.println(rc.getFullMessage());
            out.println();
            out.println(rc);

//            try (TreeWalk treeWalk = new TreeWalk(git.getRepository())) {
//                treeWalk.reset(rc.getTree().getId());
//                while (treeWalk.next()) {
//
//                    String path = treeWalk.getPathString();
//
//                    System.out.println("File: " + path + " subtree? " + treeWalk.isSubtree());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            //diffs
            if(previousCommit != null) {
                DiffCommand diffCommand = git.diff()
                        .setOldTree(getCanonicalTreeParser(previousCommit.getId(), git.getRepository()))
                        .setNewTree(getCanonicalTreeParser(rc.getId(), git.getRepository()));

                List<DiffEntry> diffEntries = diffCommand.call();
                diffEntries.forEach(de -> {
                    if(de.getChangeType() != DiffEntry.ChangeType.DELETE) {
                        out.println("CHANGE: " + de.getChangeType() + ", path: " + de.getNewPath());
                    }
                });

            }

            previousCommit = rc;
        }
        out.close();
    }
}
