package bot.farm.color_palette_helper.service;

import bot.farm.color_palette_helper.Constants;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class ButtonService {
  public List<List<InlineKeyboardButton>> createInlineButton() {
    List<List<InlineKeyboardButton>> inlineKeyButtonList = new ArrayList<>();
    List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();
    List<InlineKeyboardButton> inlineKeyboardButtonsRow2 = new ArrayList<>();
    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText("⧟ " + Constants.COMPLEMENTARY);
    inlineKeyboardButton1.setCallbackData(Constants.COMPLEMENTARY);

    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
    inlineKeyboardButton2.setText("◎ " + Constants.MONOCHROMATIC);
    inlineKeyboardButton2.setCallbackData(Constants.MONOCHROMATIC);

    InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
    inlineKeyboardButton3.setText("◡ " + Constants.ANALOGOUS);
    inlineKeyboardButton3.setCallbackData(Constants.ANALOGOUS);

    InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
    inlineKeyboardButton4.setText("△ " + Constants.TRIADIC);
    inlineKeyboardButton4.setCallbackData(Constants.TRIADIC);

    InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
    inlineKeyboardButton5.setText("▢ " + Constants.TETRADIC);
    inlineKeyboardButton5.setCallbackData(Constants.TETRADIC);

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
