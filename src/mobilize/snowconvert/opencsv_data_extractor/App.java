package mobilize.snowconvert.opencsv_data_extractor;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * OpenCSV Exporter connects to a database and performs the extraction of data
 */
public class App {
    private static ConnectionInfo connectionInfo = new ConnectionInfo();
    private static String inputFile;
    private static String outputFile;

    public static void info() {
        System.out.println("OpenCSV Data Extraction Tool");
        System.out.println("=======================");
        System.out.println("This is a simple tool to dump tables to CSV files using OpenCSV");
        System.out.println(
                "Usage: java -jar <jar file generated from compilation>.jar -host <host> -port <port> {-sid <sid> | -serviceName <serviceName>} -user <user> -password <password> -inputFile <path of the input file with the query we want to extract> -outputFile <path where the CSV file will be saved>");
    }

    public static void main(String[] args) throws IOException {
        info();
        loadInitInfo(args);

        Extractor extractor = new Extractor(connectionInfo, inputFile, outputFile);
        extractor.extractData();
    }

    private static void loadInitInfo(String[] args) {
        if (args.length % 2 != 0 || args.length < 14) {
            System.out.println("It seems like there is some inconsistency in the argument definition or some of them are missing!");
            System.exit(100);
        }

        LinkedHashMap<String, String> processedArgs = processArgs(args);
        inputFile = processedArgs.get("inputfile");
        outputFile = processedArgs.get("outputfile");

        connectionInfo.Host = processedArgs.get("host");
        connectionInfo.Port = processedArgs.get("port");
        connectionInfo.Sid = processedArgs.get("sid") == null ? "" : processedArgs.get("sid");
        connectionInfo.ServiceName = processedArgs.get("servicename") == null ? "" : processedArgs.get("servicename");
        connectionInfo.User = processedArgs.get("user");
        connectionInfo.Password = processedArgs.get("password");
    }

    private static LinkedHashMap<String, String> processArgs(String[] args) {
        LinkedHashMap<String, String> argsProcessed = new LinkedHashMap<String, String>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i].toLowerCase()) {
                case "-host":
                    i++;
                    argsProcessed.put("host", args[i]);

                    break;
                case "-port":
                    i++;
                    argsProcessed.put("port", args[i]);

                    break;
                case "-sid":
                    i++;
                    argsProcessed.put("sid", args[i]);

                    break;
                case "-servicename":
                    i++;
                    argsProcessed.put("servicename", args[i]);

                    break;
                case "-user":
                    i++;
                    argsProcessed.put("user", args[i]);

                    break;
                case "-password":
                    i++;
                    argsProcessed.put("password", args[i]);

                    break;
                case "-inputfile":
                    i++;
                    argsProcessed.put("inputfile", args[i]);

                    break;
                case "-outputfile":
                    i++;
                    argsProcessed.put("outputfile", args[i]);

                    break;
            }
        }

        return argsProcessed;
    }
}
