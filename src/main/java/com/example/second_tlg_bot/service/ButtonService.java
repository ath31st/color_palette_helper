package com.example.second_tlg_bot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.second_tlg_bot.Constants.*;

public class ButtonService {
    public List<List<InlineKeyboardButton>> createInlineButton() {
        List<List<InlineKeyboardButton>> inlineKeyButtonList = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsRow2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("\u29DF " + COMPLEMENTARY);
        inlineKeyboardButton1.setCallbackData(COMPLEMENTARY);

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("\u25ce " + MONOCHROMATIC);
        inlineKeyboardButton2.setCallbackData(MONOCHROMATIC);

        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText("\u25e1 " + ANALOGOUS);
        inlineKeyboardButton3.setCallbackData(ANALOGOUS);

        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText("\u25b3 " + TRIADIC);
        inlineKeyboardButton4.setCallbackData(TRIADIC);

        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        inlineKeyboardButton5.setText("\u25a2 " + TETRADIC);
        inlineKeyboardButton5.setCallbackData(TETRADIC);

        inlineKeyboardButtonsRow1.add(inlineKeyboardButton1);
        inlineKeyboardButtonsRow1.add(inlineKeyboardButton2);
        inlineKeyboardButtonsRow2.add(inlineKeyboardButton3);
        inlineKeyboardButtonsRow2.add(inlineKeyboardButton4);
        inlineKeyboardButtonsRow2.add(inlineKeyboardButton5);

        inlineKeyButtonList.add(inlineKeyboardButtonsRow1);
        inlineKeyButtonList.add(inlineKeyboardButtonsRow2);
        return inlineKeyButtonList;
    }

    public InlineKeyboardMarkup setInlineKeyMarkup(List<List<InlineKeyboardButton>> inlineList) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(inlineList);
        return inlineKeyboardMarkup;
    }
}
