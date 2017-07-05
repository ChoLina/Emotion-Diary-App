package com.example.choisung.emotiondiary;

/**
 * Created by ChoISung on 2017-06-20.
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SentiWordNet {

    private Map<String, Double> dictionary;

//    private String pathToSWN = "C:\\Users\\ChoISung\\AndroidStudioProjects\\diary\\EmotionDiary\\app\\src\\main\\assets\\SentiWordNet_3.0.0_20130122.txt";
//    private File file = new File("C:\\Users\\ChoISung\\Downloads\\SentiWordNet_3.0.0\\home\\swn\\www\\admin\\dump\\SentiWordNet_3.0.0_20130122.txt");
//    SentiWordNet_3.0.0_20130122.txt
    public SentiWordNet(InputStream txt){

        // This is our main dictionary representation
        dictionary = new HashMap<String, Double>();

        // From String to list of doubles.
        HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

        BufferedReader csv = null;
        try {

            csv = new BufferedReader(new InputStreamReader(txt));
            int lineNumber = 0;

            String line;
            while ((line = csv.readLine()) != null) {
                lineNumber++;

                // If it's a comment, skip this line.
                if (!line.trim().startsWith("#")) {
                    // We use tab separation
                    String[] data = line.split("\t");
                    String wordTypeMarker = data[0];

                    // Example line:
                    // POS ID PosS NegS SynsetTerm#sensenumber Desc
                    // a 00009618 0.5 0.25 spartan#4 austere#3 ascetical#2
                    // ascetic#2 practicing great self-denial;...etc

                    // Is it a valid line? Otherwise, through exception.
//                    if (data.length != 6) {
//                        throw new IllegalArgumentException(
//                                "Incorrect tabulation format in file, line: " + lineNumber);
//                    }

                    // Calculate synset score as score = PosS - NegS
                    Double synsetScore = Double.parseDouble(data[2]) - Double.parseDouble(data[3]);

                    // Get all Synset terms
                    String[] synTermsSplit = data[4].split(" ");

                    // Go through all terms of current synset.
                    for (String synTermSplit : synTermsSplit) {
                        // Get synterm and synterm rank
                        String[] synTermAndRank = synTermSplit.split("#");
                        String synTerm = synTermAndRank[0] + "#" + wordTypeMarker;

                        int synTermRank = Integer.parseInt(synTermAndRank[1]);
                        // What we get here is a map of the type:
                        // term -> {score of synset#1, score of synset#2...}

                        // Add map to term if it doesn't have one
                        if (!tempDictionary.containsKey(synTerm)) {
                            tempDictionary.put(synTerm, new HashMap<Integer, Double>());
                        }

                        // Add synset link to synterm
                        tempDictionary.get(synTerm).put(synTermRank, synsetScore);
                    }
                }
            }

            // Go through all the terms.
            for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary.entrySet()) {
                String word = entry.getKey();
                Map<Integer, Double> synSetScoreMap = entry.getValue();

                // Calculate weighted average. Weigh the synsets according to
                // their rank.
                // Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
                // Sum = 1/1 + 1/2 + 1/3 ...
                double score = 0.0;
                double sum = 0.0;
                for (Map.Entry<Integer, Double> setScore : synSetScoreMap.entrySet()) {
                    score += setScore.getValue() / (double) setScore.getKey();
                    sum += 1.0 / (double) setScore.getKey();
                }
                score /= sum;

                dictionary.put(word, score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public double extract(String word) {
        Double total = new Double(0);
        Log.d("dictionary", dictionary.get(word+"#n")+"");
        if(dictionary.get(word+"#n") != null)
            total += dictionary.get(word+"#n");
        if(dictionary.get(word+"#a") != null)
            total += dictionary.get(word+"#a");
        if(dictionary.get(word+"#r") != null)
            total += dictionary.get(word+"#r");
        if(dictionary.get(word+"#v") != null)
            total += dictionary.get(word+"#v");

        return total;
    }

    public String classifyContents(String contents){
        Log.d("contents", contents);
        String[] words = contents.split("\\s+");
        double totalScore = 0;
        for(String word : words) {
            word = word.replaceAll("([^a-zA-Z\\s])", "");
            totalScore += extract(word);
        }
        Log.d("totalScore", totalScore+"");

        if(totalScore>=0.25)
            return  "positive";
        else if(totalScore < 0)
            return "negative";
        return "neutral";
    }

}