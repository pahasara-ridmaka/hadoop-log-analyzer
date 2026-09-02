package com.github.pahasararidmaka.loganalyzer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phsr
 */

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class LogMapper extends Mapper<LongWritable, Text, Text, Text> {
	private static final Pattern LOG_PATTERN =  Pattern.compile("^(\\S+) \\S+ \\S+ \\[.*?\\] \".*?\" (\\d{3}) (\\d+)");

	private final Text outKey = new Text();
	private final Text outValue = new Text();


	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
	
		Matcher matcher = LOG_PATTERN.matcher(value.toString());

		if (matcher.find()) {
			String ip = matcher.group(1);
			String statusCode = matcher.group(2);
			String bytes = matcher.group(3);

			outKey.set(ip);
			outValue.set(statusCode+','+bytes);
			context.write(outKey, outValue);
		}
	}
    
}
