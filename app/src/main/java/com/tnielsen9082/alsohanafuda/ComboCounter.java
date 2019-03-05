package com.tnielsen9082.alsohanafuda;

import android.widget.LinearLayout;

import java.util.ArrayList;

public class ComboCounter {
    public int[] countUp(LinearLayout[] tricks, boolean wipe){
        ComboList combo = new ComboList();
        //make an arrayList of cards for the first player
        ArrayList<CardImage> cardsOne = new ArrayList<>();
        for (int i = 0; i < tricks[0].getChildCount(); i++) {
            //mon is the letter corresponding to the month of the card
            //sco is the point value of the card
            CardImage images = new CardImage(parseMon(tricks[0],i),parseSco(tricks[0],i));
            cardsOne.add(images);
        }
        ArrayList<CardImage> cardsTwo = new ArrayList<>();
        for (int i = 0; i < tricks[1].getChildCount(); i++) {
            CardImage images = new CardImage(parseMon(tricks[1],i),parseSco(tricks[1],i));
            cardsTwo.add(images);
        }
        ArrayList<CardImage> cardsThree = new ArrayList<>();
        for (int i = 0; i < tricks[2].getChildCount(); i++) {
            CardImage images = new CardImage(parseMon(tricks[2],i),parseSco(tricks[2],i));
            cardsThree.add(images);
        }
        //the wipe boolean is to specify if the rain combo invalidates all combos
        //if it's true and a player has all four rain cards the method returns -1
        int[] comboPlus= {combo.checker(cardsOne,wipe),combo.checker(cardsTwo,wipe),combo.checker(cardsThree,wipe)};
        boolean rain = false;
        for (int rainCheck : comboPlus) {
            if (rainCheck == -1) {
                rain = true;
            }
        }
        if(!rain){
            return comboPlus;
        }
        else{
            return new int[]{0,0,0};
        }

    }
    public String parseMon(LinearLayout cardHolder,int i){
        String month;
        month =cardHolder.getChildAt(i).getContentDescription().charAt(0)+"";
        return month;
    }
    public int parseSco(LinearLayout cardHolder,int i){
        int score;
        score =Integer.parseInt(String.valueOf(cardHolder.getChildAt(i).getContentDescription().subSequence(1,3)));
        return score;

    }
}
