/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package top10flight;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author priyank
 */
public class Top25MoviesChaining {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        //job 1
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Job: 1, top 25 movie based on ratings");
        job.setJarByClass(Top25MoviesChaining.class);
        job.setMapperClass(FirstMapper.class);
        job.setReducerClass(FirstReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[1]));
        Path firstJobOutput = new Path(args[2]);
        FileOutputFormat.setOutputPath(job, firstJobOutput);
        job.waitForCompletion(true);
        
        //job 2
        Job job2 = Job.getInstance(conf, "Job: 2, top 25 movie based on ratings");
        job2.setJarByClass(Top25MoviesChaining.class);
        job2.setMapperClass(SecondMapper.class);
        job2.setReducerClass(SecondReducer.class);
        job2.setSortComparatorClass(SortKeyComparator.class);
        job2.setMapOutputKeyClass(DoubleWritable.class);
        job2.setMapOutputValueClass(Text.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.addInputPath(job2, firstJobOutput);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss", Locale.US).
                format(new Timestamp(System.currentTimeMillis()));
        FileOutputFormat.setOutputPath(job2, new Path(args[2] + timeStamp));
        System.exit(job2.waitForCompletion(true) ? 0 : 1);

    }

}
