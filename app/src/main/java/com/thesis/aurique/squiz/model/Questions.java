package com.thesis.aurique.squiz.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Questions {

    public String question;
    public String question_dt_added;
    public String uId;
    public String question_id;
    public List<String> options;
    public String answer;
    public String category;

//    public Questions(String question,
//                     String question_dt_added,
//                     String uId,
//                     String question_id,
//                     List<String> options,
//                     String answer,
//                     String category){
//
//        this.question = question;
//        this.question_dt_added = question_dt_added;
//        this.uId = uId;
//        this.question_id = question_id;
//        this.options = options;
//        this.answer = answer;
//        this.category = category;
//
//   }

    public Map<String, Object> toMap(){
        Map<String,Object> questionObject = new HashMap<>();

        questionObject.put("question",question);
        questionObject.put("question_dt_added",question_dt_added);
        questionObject.put("uId",uId);
        questionObject.put("question_id",question_id);
        questionObject.put("options",options);
        questionObject.put("answer",answer);
        questionObject.put("category",category);

        return  null;
    }

    public List<String> toListOption(String optionone,String optiontwo,String optionthree,String optionfour) {

        List<String> optionList = new ArrayList<>();
        optionList.add(optionone);
        optionList.add(optiontwo);
        optionList.add(optionthree);
        optionList.add(optionfour);

        return optionList;

    }
}
