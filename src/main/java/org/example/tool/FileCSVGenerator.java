package org.example.tool;

import org.example.entities.JavaClass;
import org.example.entities.Release;
import org.example.entities.Ticket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileCSVGenerator {
    private final String directoryPath;
    private final String projName;
    private final static String TESTING = "testing" + File.separator;
    private final static String TRAINING = "training" + File.separator;
    private final static String OTHERFILES = "otherFiles" + File.separator;
    private final static String FIVE_RUN_TRAINING = TRAINING + "five_Run" + File.separator;
    private final static String THREE_RUN_TRAINING = TRAINING + "three_Run" + File.separator;
    private final static String FIVE_RUN_TESTING = TESTING + "five_Run" + File.separator;
    private final static String THREE_RUN_TESTING = TESTING + "three_Run" + File.separator;
    private final static String TRAINING_CSV_FIVE = FIVE_RUN_TRAINING + "CSV" + File.separator;
    private final static String TRAINING_ARFF_FIVE = FIVE_RUN_TRAINING + "ARFF" + File.separator;
    private final static String TRAINING_CSV_THREE = THREE_RUN_TRAINING + "CSV" + File.separator;
    private final static String TRAINING_ARFF_THREE = THREE_RUN_TRAINING + "ARFF" + File.separator;
    private final static String TESTING_CSV_FIVE = FIVE_RUN_TESTING + "CSV" + File.separator;
    private final static String TESTING_ARFF_FIVE = FIVE_RUN_TESTING + "ARFF" + File.separator;
    private final static String TESTING_CSV_THREE = THREE_RUN_TESTING + "CSV" + File.separator;
    private final static String TESTING_ARFF_THREE = THREE_RUN_TESTING + "ARFF" + File.separator;

    public FileCSVGenerator(String directoryPath, String projName) throws IOException {
        this.projName = projName;
        this.directoryPath = directoryPath + projName.toLowerCase() + File.separator;

        Path path = Paths.get(directoryPath);
        Path pathTraining = Paths.get(this.directoryPath + TRAINING);
        Path pathFiveRunTrain = Paths.get(this.directoryPath + FIVE_RUN_TRAINING);
        Path pathThreeRunTrain = Paths.get(this.directoryPath + THREE_RUN_TRAINING);
        Path pathFiveRunTest = Paths.get(this.directoryPath + FIVE_RUN_TRAINING);
        Path pathThreeRunTest = Paths.get(this.directoryPath + THREE_RUN_TRAINING);
        Path pathOther = Paths.get(this.directoryPath + OTHERFILES);
        Path pathTesting = Paths.get(this.directoryPath + TESTING);
        Path pathTrainingCSV_five = Paths.get(this.directoryPath + TRAINING_CSV_FIVE);
        Path pathTrainingARFF_five = Paths.get(this.directoryPath + TRAINING_ARFF_FIVE);
        Path pathTrainingCSV_three = Paths.get(this.directoryPath + TRAINING_CSV_THREE);
        Path pathTrainingARFF_three = Paths.get(this.directoryPath + TRAINING_ARFF_THREE);
        Path pathTestingCSV_five = Paths.get(this.directoryPath + TESTING_CSV_FIVE);
        Path pathTestingARFF_five = Paths.get(this.directoryPath + TESTING_ARFF_FIVE);
        Path pathTestingCSV_three = Paths.get(this.directoryPath + TESTING_CSV_THREE);
        Path pathTestingARFF_three = Paths.get(this.directoryPath + TESTING_ARFF_THREE);

        if (!Files.exists(path))
            Files.createDirectories(path);

        if (!Files.exists(pathTraining))
            Files.createDirectories(pathTraining);

        if (!Files.exists(pathTesting))
            Files.createDirectories(pathTesting);

        if (!Files.exists(pathOther))
            Files.createDirectories(pathOther);

        if (!Files.exists(pathFiveRunTest))
            Files.createDirectories(pathFiveRunTest);

        if (!Files.exists(pathThreeRunTest))
            Files.createDirectories(pathThreeRunTest);

        if (!Files.exists(pathFiveRunTrain))
            Files.createDirectories(pathFiveRunTrain);

        if (!Files.exists(pathThreeRunTrain))
            Files.createDirectories(pathThreeRunTrain);

        if (!Files.exists(pathTrainingCSV_five))
            Files.createDirectories(pathTrainingCSV_five);

        if (!Files.exists(pathTrainingARFF_five))
            Files.createDirectories(pathTrainingARFF_five);

        if (!Files.exists(pathTrainingCSV_three))
            Files.createDirectories(pathTrainingCSV_three);

        if (!Files.exists(pathTrainingARFF_three))
            Files.createDirectories(pathTrainingARFF_three);

        if (!Files.exists(pathTestingARFF_five))
            Files.createDirectories(pathTestingARFF_five);

        if (!Files.exists(pathTestingCSV_five))
            Files.createDirectories(pathTestingCSV_five);

        if (!Files.exists(pathTestingARFF_three))
            Files.createDirectories(pathTestingARFF_three);

        if (!Files.exists(pathTestingCSV_three))
            Files.createDirectories(pathTestingCSV_three);
    }

    private void writeToFile(FileWriter fileWriter, String content) throws IOException {
        fileWriter.append(content);
        fileWriter.append("\n");
    }

    private void closeWriter(FileWriter fileWriter) {
        if (fileWriter != null) {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    public void generateReleaseInfo(List<Release> releases) {
        FileWriter fileWriter = null;

        try {
            String fileTitle = this.directoryPath + OTHERFILES + this.projName + "_releaseList.csv";

            fileWriter = new FileWriter(fileTitle);

            writeToFile(fileWriter, "Index,Version ID,Version Name,Date");

            for (int i = 0; i < releases.size(); i++) {
                Release release = releases.get(i);
                int index = i + 1;

                writeToFile(fileWriter, index + "," + release.getVersionID() + "," +
                        release.getName() + "," + release.getDate().toString());
            }

        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "An error occurred while generating release info", e);
        } finally {
            closeWriter(fileWriter);
        }
    }

    public void generateTicketInfo(List<Ticket> ticketList) {
        FileWriter fileWriter = null;

        try {
            String fileTitle = this.directoryPath + OTHERFILES + this.projName + "_ticketList.csv";
            fileWriter = new FileWriter(fileTitle);

            writeToFile(fileWriter, "Key,Injected Version,Opening Version,Fixed Version, Affected Version List");

            for (Ticket ticket : ticketList) {
                String affectedVersions = getAffectedVersionsAsString(ticket);
                writeToFile(fileWriter, ticket.getTicketKey() + "," + ticket.getInjectedVersion().getName() + "," +
                        ticket.getOpeningVersion().getName() + "," + ticket.getFixedVersion().getName() + "," +
                        affectedVersions);
            }

        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } finally {
            closeWriter(fileWriter);
        }
    }

    private String getAffectedVersionsAsString(Ticket ticket) {
        List<Release> affectedVersions = ticket.getAffectedVersionsList();
        StringBuilder stringBuilder = new StringBuilder();
        for (Release release : affectedVersions) {
            stringBuilder.append(release.getName()).append(" ");
        }
        return stringBuilder.toString();
    }

    public void generateTrainingSet(List<Release> releaseList, int walk) {
        FileWriter fileWriter = null;

        try {
            String fileTitle = this.directoryPath + TRAINING_CSV_FIVE + this.projName + "_trainingSet" + walk + ".csv";
            fileWriter = new FileWriter(fileTitle);

            writeToFile(fileWriter, "Version ID,Class Name,LOC,LOC_touched,Age,Revision number,Fix number,Author Number,LOC_added,MAX_LOC_added,AVG_LOC_added,churn,MAX_churn,Buggyness");

            for (int i = 0; i < releaseList.size(); i++) {
                Release release = releaseList.get(i);
                int index = i + 1;

                for (JavaClass javaClass : release.getJavaClassList()) {
                    writeClasses(fileWriter, index, javaClass);
                }
            }

        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } finally {
            closeWriter(fileWriter);
        }
    }

    public void generateTestingSet(List<JavaClass> javaClassList, int testIndex) {
        FileWriter fileWriter = null;

        try {
            String fileTitle = this.directoryPath + TESTING_CSV_FIVE + this.projName + "_testingSet" + testIndex + ".csv";
            fileWriter = new FileWriter(fileTitle);

            writeToFile(fileWriter, "Version ID,Class Name,LOC,LOC_touched,Age,Revision number,Fix number,Author Number,LOC_added,MAX_LOC_added,AVG_LOC_added,churn,MAX_churn,Buggyness");

            for (JavaClass javaClass : javaClassList) {
                writeClasses(fileWriter, testIndex+1, javaClass);
            }

        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } finally {
            closeWriter(fileWriter);
        }
    }

    private void writeClasses(FileWriter fileWriter, int index, JavaClass javaClass) throws IOException {
        writeToFile(fileWriter, index + "," + javaClass.getName() + "," +
                javaClass.getLocSize() + "," + javaClass.getLocTouched() + "," +
                javaClass.getAge().getYears() + "," + javaClass.getRevisionNumber() + "," +
                javaClass.getFixNumber() + "," + javaClass.getAuthorNumber() + "," +
                javaClass.getLocAdded() + "," + javaClass.getMaxLOCAdded() + "," +
                javaClass.getAvgLOCAdded() + "," + javaClass.getChurn() + "," +
                javaClass.getMaxChurn() + "," + javaClass.getBuggy());
    }
}
