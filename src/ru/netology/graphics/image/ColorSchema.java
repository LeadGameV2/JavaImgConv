package ru.netology.graphics.image;

public class ColorSchema implements TextColorSchema {
    @Override
    public char convert(int color) {
        char toReturn = ' ';
        if (color <= 51) {
            toReturn = '█';
        } else if (color <= 102) {
            toReturn = '▓';
        } else if (color <= 153) {
            toReturn = '▒';
        } else if (color <= 204) {
            toReturn = '░';
        } else {
            toReturn = '-';
        }


        return toReturn;
    }
}
