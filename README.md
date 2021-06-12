# OpenCSV Extraction Tool

The OpenCSV Extraction tool is intended to be used as a data extraction tool. It has been written in Java and it uses the [OpenCSV parser library](http://opencsv.sourceforge.net/). The development of this tool was done as part of R&D of the [Oracle Data Extraction Tool](https://github.com/MobilizeNet/SnowConvertDataExportScripts/tree/main/Oracle). 

This tool follows three simple steps:
1. It takes a SQL file containing one query as an input.
2. Then, we connect to the database using the parameters send to the tool.
3. Finally, we extract the data and dump it to the specified CSV file.

## Usage

As this is a Java tool, you can run the tool using a command line terminal with some arguments/parameters. You should use the following command as a reference:

```
java -jar <path and name of the jar file>.jar -host <host> -port <port> [-sid <sid> | -serviceName <serviceName>] -user <user> -password <password> -inputFile <path and filename of the file with query to execute> -outputFile <path and filename of the CSV file where we will dump the data>
```

All the parameters shown are required, with the exception of `-sid` and `-serviceName` that only one of them can be missing, but not both.

Also, you can use the following command as an example:

```
java -jar opencsv_tool.jar -host localhost -port 1521 -serviceName orcl -user sh -password sh -inputFile ./queries/query1.sql -outputFile ./output/datadump_query1.csv
```

## Future work

As of now, there is support to extract data only from Oracle databases and for one file at a time. The intention is to add support for more database providers, but that is still in progress.