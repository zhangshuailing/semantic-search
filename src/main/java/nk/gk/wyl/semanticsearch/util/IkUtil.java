package nk.gk.wyl.semanticsearch.util;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import java.util.ArrayList;
import java.util.List;

public class IkUtil {
    private static WordSegmenter wordSegmenter =null;

    public static WordSegmenter getWordSegmenter() {
        return wordSegmenter;
    }

    public static void setWordSegmenter(WordSegmenter wordSegmenter) {
        IkUtil.wordSegmenter = wordSegmenter;
    }

    public IkUtil(){
        WordSegmenter wordSegmenter = new WordSegmenter();
        wordSegmenter.seg("text");
        setWordSegmenter(wordSegmenter);
    }
    public static void main(String[] args) {
        String text = "姚明有多高？";
        try {
            System.out.println(getStringList(text));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getStringList(String text) throws Exception{
        List<Word> words = wordSegmenter.seg(text);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            result.add(word.toString());
        }
        return result;
    }
}
