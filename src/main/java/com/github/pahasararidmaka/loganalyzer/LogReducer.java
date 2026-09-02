/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.github.pahasararidmaka.loganalyzer;

/**
 *
 * @author phsr
 */

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class LogReducer extends Reducer<Text, Text, Text, Text>{

	private static final Text result = new Text();

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		long totalRequests = 0;
		long totalBytes = 0;
		long errorCount = 0;


		for (Text val : values ) {
			String[] parts = val.toString().split(",");
			if (parts.length == 2 ){
				int status = Integer.parseInt(parts[0]);
				long bytes = Long.parseLong(parts[1]);

				totalRequests++;
				totalBytes += bytes;
				if (status >= 400){
					errorCount++;
				}
			}
		}

		double errorRate = (totalRequests > 0) ? ((double) errorCount / totalRequests) * 100.0 : 0.0;
		result.set(String.format("Requsts: %d\tBytes: %d\tErrorRate: %.2f%%", totalRequests, totalBytes, errorRate));
		context.write(key, result);
	}
	
}
