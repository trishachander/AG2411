package se.kth.ag2411.mapalgebra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Layer {
	// Attributes (This is complete)
	public String name; // name of this layer
	public int nRows; // number of rows
	public int nCols; // number of columns
	public double xllcorner;
	public double yllcorner;
	public double resolution; // cell size
	public double[][] values; // data. Alternatively, public double[][] values;
	public double nullValue; // value designated as "No data"


	//Constructor (This is not complete)
	public Layer(String layerName, String fileName) {
		// You may want to do some work before reading a file.

		String words[];


		try { // Exception may be thrown while reading (and writing) a file.
			// Get access to the lines of Strings stored in the file      
			File rFile = new File(fileName);
			FileReader fReader = new FileReader(rFile);
			BufferedReader bufReader = new BufferedReader(fReader);		    
			String line = null;


			// Read first line, which starts with "ncols"
			line = bufReader.readLine();
			if (line != null) {
				// split line into words separated by space
				words = line.split(" ");
				// get last word in array of words
				String numValue = words[words.length - 1];
				// try to convert String to Int
				nCols = Integer.parseInt(numValue);
			}
			// Read second line, which starts with "nrows"
			line = bufReader.readLine();
			if (line != null) {
				// split line into words separated by space
				words = line.split(" ");
				// get last word in array of words
				String numValue = words[words.length - 1];
				// try to convert String to Int
				nRows = Integer.parseInt(numValue);
			}
			// Read third line, which starts with "xllcorner"
			line = bufReader.readLine();
			if (line != null) {
				words = line.split(" ");
				String numValue = words[words.length - 1];
				xllcorner = Double.parseDouble(numValue);
			}
			// Read forth line, which starts with "yllcorner"
			line = bufReader.readLine();
			if (line != null) {
				words = line.split(" ");
				String numValue = words[words.length - 1];
				yllcorner = Double.parseDouble(numValue);
			}
			// Read fifth line, which starts with "cellsize"
			line = bufReader.readLine();
			if (line != null) {
				words = line.split(" ");
				String numValue = words[words.length - 1];
				resolution = Double.parseDouble(numValue);
			}
			// Read sixth line, which starts with "NODATA_value"
			line = bufReader.readLine();
			if (line != null) {
				words = line.split(" ");
				String numValue = words[words.length - 1];
				nullValue = Double.parseDouble(numValue);
			}
			// Read each of the remaining lines, which represents a row of raster
			//values
			values = new double[nRows][nCols];

			for(int i = 0; i<nRows;i++) {

				line = bufReader.readLine();

				words = line.split(" ");

				for(int j=0; j<nCols;j++) {
					double value = Double.parseDouble(words[j]);

					values[i][j] = value;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void print(){
		//Print this layer to console
		System.out.println("ncols "+nCols);
		System.out.println("nrows "+nRows);
		System.out.println("xllcorner "+xllcorner);
		System.out.println("yllcorner "+yllcorner);
		System.out.println("cellsize "+resolution);
		System.out.println("NODATA_value " + nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				System.out.print(values[i][j]+" ");
			}
			System.out.println();
		}
	}
	// Save (This is not complete)
	@SuppressWarnings("resource")
	public void save(String outputFileName) {
		// save this layer as an ASCII file that can be imported to ArcGIS
		BufferedWriter bufWriter = null;

		try{
			bufWriter = new BufferedWriter(new FileWriter(outputFileName));
			String line = null;

			//writing 'nCols'
			line = "ncols			" + nCols;
			bufWriter.write(line);
			bufWriter.newLine();

			//writing 'nCols'
			line = "nrows			" + nRows;
			bufWriter.write(line);
			bufWriter.newLine();

			//writing 'xllcorner'
			line = "xllcorner			" + xllcorner;
			bufWriter.write(line);
			bufWriter.newLine();

			//writing 'yllcorner'
			line = "yllcorner			" + yllcorner;
			bufWriter.write(line);
			bufWriter.newLine();

			//writing 'cellsize'
			line = "cellsize 		" + resolution;
			bufWriter.write(line);
			bufWriter.newLine();

			//writing no data values
			line = "NODATA_value 		" + nullValue;
			bufWriter.write(line);
			bufWriter.newLine();

			//writing the rest of the values
			for (int i = 0; i < nRows; i++) { 
				line = "";

				for (int j = 0; j < nCols; j++) {
					line += values[i][j] + " ";
				}

				bufWriter.write(line);

				bufWriter.newLine();
			}

		}catch(NumberFormatException ex){ex.printStackTrace();

		} catch (IOException ex) {
			ex.printStackTrace();

		} finally {
			try {
				bufWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

