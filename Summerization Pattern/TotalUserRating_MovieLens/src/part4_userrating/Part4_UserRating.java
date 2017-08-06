/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part4_userrating;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/** 
 *
 * @author Rajat
 */
public class Part4_UserRating {

    /**
     * @param args the command line arguments
     */
    public static class TokenizerMapper
            extends Mapper<LongWritable, Text, IntWritable, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private IntWritable users = new IntWritable();

        public void map(LongWritable key, Text value, Context context
        ) throws IOException, InterruptedException {

            final String[] data = value.toString().split("::");            
            if (data.length > 1) {
                final String users_movie = data[0];
                users.set((Integer.parseInt(users_movie)));
                context.write(users, one);
            }
        }
    }

    public static class IntSumReducer
            extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {

        private IntWritable rating_count = new IntWritable();

        public void reduce(IntWritable key, Iterable<IntWritable> values,
                Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            rating_count.set(sum);
            context.write(key, rating_count);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "User_Rating");
        job.setJarByClass(Part4_UserRating.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}
