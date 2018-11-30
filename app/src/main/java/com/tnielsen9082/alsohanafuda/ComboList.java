package com.tnielsen9082.alsohanafuda;

public class ComboList {
    //the list of combeaux
    /*
    brights a20 c20 h20 k20 l20 -100
    brights a20 c20 h20 l20 -60
    seven of a05 b05 c05 d05 e05 f05 g05 i05 j05 -40
    all three of a05 b05 c05 -40
    all three of f05 i05 j05 -40
    two or three of h20 i10 c20 -20/40
    6 of a05 b05 c05 d05 e05 f05 g05 i05 j05 -30
    a20 b10 c20 -30
    a20 l20 h20 -20
    g10 j10 f10 -20
    d05 e05 g05 -20
    d10 d05 d01 d01 -10
    l20 l01 l01 l01 -10
    k20 k10 k05 k01 -10, may invalidate other combos
     */
    public int checker(cardImage[] hand,boolean wipe){
        int tanzakuTot =0;
        int brights =0;
        int threeBright=0;
        int rainPoet=0;
        int tanzakuRedLet=0;
        int tanzakuBlue=0;
        int wisteria =0;
        int rain =0;
        int paul = 0;
        int views=0;
        int cpm=0;
        int bdb=0;
        int tanzakuG =0;
        for (int i = 0; i < hand.length; i++) {
            cardImage card =hand[i];
            //counts if the card is a wisteria
            if(card.compare("d")){
                wisteria++;
            }
            //a paulownia
            if(card.compare("l")){
                paul++;
            }
            //or a rain
            if(card.compare("k")){
                rain++;
            }
            //counts the total number of non-rain tanzaku
            if(card.compare(5)&&!card.compare("k")){
                tanzakuTot++;
                //and the lettered tanzaku
                if(card.compare("a")||card.compare("b")||card.compare("c")){
                    tanzakuRedLet++;
                }
                //the blue tanzaku
                if(card.compare("f")||card.compare("i")||card.compare("j")){
                    tanzakuBlue++;
                }
                //the plain tanzaku
                if(card.compare("d")||card.compare("e")||card.compare("g")){
                    tanzakuG++;
                }
            }
            //count the four brights
            if(card.compare("a",20)||card.compare("c",20)||card.compare("h",20)||card.compare("l",20)){
                brights++;
            }
            //count the poet
            if (card.compare("k",20)){
                rainPoet++;
            }
            //count the three brights
            if(card.compare("b",10)||card.compare("a",20)||card.compare("c",20)){
                threeBright++;
            }
            //count crane-phoenix-moon
            if(card.compare("a",20)||card.compare("l",20)||card.compare("h",20)){
                cpm++;
            }
            //count boar-deer-butterfly
            if(card.compare("g",10)||card.compare("j",10)||card.compare("f",10)){
                bdb++;
            }
            //count the views
            if(card.compare("h",20)||card.compare("c",20)||card.compare("i",10)){
                views++;
            }
        }
        return counter(tanzakuTot,
        brights,
        threeBright,
        rainPoet,
        tanzakuRedLet,
        tanzakuBlue,
        wisteria,
        rain,
        paul,
        views,
        cpm,
        bdb,
        tanzakuG, wipe);
    }
    public int counter (int tanzakuTot,
            int brights,
            int threeBright,
            int rainPoet,
            int tanzakuRedLet,
            int tanzakuBlue,
            int wisteria,
            int rain,
            int paul,
            int views,
            int cpm,
            int bdb,
            int tanzakuG,
                        boolean wipe){
        int total =0;
        if(tanzakuTot==7){
            total+=40;
        }
        if(tanzakuTot==6){
            total+=30;
        }
        if(cpm==3){
            total+=20;
        }
        if(bdb==3){
            total+=20;
        }
        if(tanzakuG==3){
            total+=20;
        }
        if(wisteria==4){
            total+=10;
        }
        if(paul==4){
            total+=10;
        }
        if(tanzakuBlue==3){
            total+=40;
        }
        if(tanzakuRedLet==3){
            total+=40;
        }
        if(brights==4){
            if(rainPoet==1){
                total+=100;
            }
            else{
                total+=60;
            }
        }
        if(threeBright==3){
            total+=30;
        }
        if(views==3){
            total+=40;
        }
        if(views==2){
            total+=20;
        }
        if (rain==4){
            if(wipe){
                total=0;
            }
            else{
                total+=10;
            }
        }
        return total;
    }
}
