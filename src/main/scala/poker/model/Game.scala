package model

object Game {

    private[Game] def getSymbols() : List[Symbol] = {
        val symbols = List(Symbol.HEART, Symbol.DIAMOND, Symbol.CLUB, Symbol.SPADE)
        symbols
    }

    private[Game] def getPicutres() : List[Symbol] = {
        val pictures = List(Picture.TWO, Picture.THREE, Picture.FOUR, Picture.FIVE, Picture.SIX, Picture.SEVEN, 
        Picture.EIGHT, Picture.NINE, Picture.TEN, Picture.JACK, Picture.QUEEN, Picture.KING, Picture.ACE)
        pictures
    } 

    private[Game] def createCards() : List[Cards] = {
        
    }

}

