/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package top25products;

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


public class Top25ProductChaining {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        //job 1
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Job: 1, top 25 Products based on ratings");
        job.setJarByClass(top25products.Top25ProductChaining.class);
        job.setMapperClass(FirstMapper.class);
        job.setReducerClass(FirstReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        Path firstJobOutput = new Path(args[1]);
        FileOutputFormat.setOutputPath(job, firstJobOutput);
        job.waitForCompletion(true);
        
        //job 2
        Job job2 = Job.getInstance(conf, "Job: 2, top 25 Products based on ratings");
        job2.setJarByClass(top25products.Top25ProductChaining.class);
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
        FileOutputFormat.setOutputPath(job2, new Path(args[1] + timeStamp));
        System.exit(job2.waitForCompletion(true) ? 0 : 1);
    }

}
