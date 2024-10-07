# Factorial Calculator

This is a console-based multithreaded program that calculates factorials for each number from an input file.

## Features

1. Multithreaded: The program uses multiple threads to read data from the input file, perform calculations, and write
   data to the output file.
2. Input/Output: The program reads numbers from `input.txt` and writes the calculated factorials to `output.txt` in the
   format "x=x!".
3. Thread Pool: The size of the thread pool performing the calculations can be set via console arguments.
4. Order Preservation: The output data is in the same order as the input data.
5. Rate Limiting: A delay is added to ensure that no more than 100 factorials are calculated per second.
6. Large numbers: The program can calculate factorials of large numbers, but were tested up to 10_000!. Large numbers
   significantly increase the calculation time, memory usage and output file size.

## Building

This project uses Maven for building. To build the project, run `mvn clean package` in the project root directory. 

## Running
In target directory run java -jar factorial-calculator-1.0-SNAPSHOT.jar [OPTIONS]
## Options
- `-f` or `--file`: Set the input file name.

- `-t` or `--threads`: Set the number of threads in the pool.
- `-se` or `--skip-empty`: Skip empty lines in the input file.
- `-ss` or `--skip-strings`: Skip lines that cannot be parsed as integers in the input file.
- `-dbg` or `--debug`: Enable debug mode (Only for development purposes).
- `-sd` or `--skip-delays`: Skip the delay between calculations.