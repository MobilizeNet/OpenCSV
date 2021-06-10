package mobilize.snowconvert.opencsv_data_extractor;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;

public class Extractor {
    private ConnectionInfo connectionInfo;
    private String inputFile;
    private String outputFile;

    public Extractor(ConnectionInfo connectionInfo, String inputFile, String outputFile) {
        this.connectionInfo = connectionInfo;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void extractData() {
        Connection con = connect(connectionInfo);

        ResultSet rs = getResultSet(con, inputFile);

        dumpDataToCSVFile(rs, outputFile);
    }

    private Connection connect(ConnectionInfo connectionInfo) {
        Connection conn = null;

        try {
            String server = "";

            if (!connectionInfo.Sid.trim().isEmpty() && !connectionInfo.Sid.equals("null")) {
                server = "@" + connectionInfo.Host + ":" + connectionInfo.Port + ":" + connectionInfo.Sid;
            } else if (!connectionInfo.ServiceName.toString().trim().isEmpty() && !connectionInfo.ServiceName.equals("null")) {
                server = "@" + connectionInfo.Host + ":" + connectionInfo.Port + "/" + connectionInfo.ServiceName;
            }

            System.out.println("Connecting to: " + server);

            DriverManager.registerDriver (new oracle.jdbc.OracleDriver());
            conn = DriverManager.getConnection("jdbc:oracle:thin:" + server, connectionInfo.User,
                    connectionInfo.Password);

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    private ResultSet getResultSet(Connection con, String inputFile) {
        ResultSet resultSet = null;

        try {
            Statement stat = con.createStatement();
            String query = readFileContent(inputFile);

            resultSet = stat.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    private String readFileContent(String filePath) {
        String content = null;

        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    private void dumpDataToCSVFile(ResultSet resultSet, String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile);

            CSVWriterBuilder builder = new CSVWriterBuilder(fw);
            ICSVWriter csvWriter = builder.build();

            csvWriter.writeAll(resultSet, true);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
