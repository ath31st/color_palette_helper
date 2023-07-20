package bot.farm.color_palette_helper.service;

import bot.farm.color_palette_helper.Constants;
import bot.farm.color_palette_helper.Palette;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public record SendMessageService(ButtonService buttonService, Palette palette) {
  public SendMessage createMenuMessage(String chatId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(Constants.MENU);
    InlineKeyboardMarkup inlineKeyboardMarkup = buttonService.setInlineKeyMarkup(buttonService.createInlineButton());
    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    return sendMessage;
  }

  public EditMessageText createEditMessage(Update update, String editMessage) {
    EditMessageText editMessageText = new EditMessageText();
    Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
    String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
    editMessageText.setChatId(chatId);
    editMessageText.setMessageId(messageId);
    editMessageText.setText(editMessage);
    return editMessageText;
  }

  public SendMessage createMessage(String chatId, String message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.enableHtml(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    return sendMessage;
  }

  public SendPhoto createPhotoMessage(String chatId) {
    SendPhoto sendPhoto = new SendPhoto();
    sendPhoto.setChatId(chatId);
    sendPhoto.setPhoto(new InputFile(palette.saveImageToInputStream(), chatId));
    return sendPhoto;
  }
}
