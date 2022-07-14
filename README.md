## Сolor palette helper 

A bot for generating color palettes. An analogue of the online services of the Itten's color wheel

#### Project objectives:
Develop a telegram bot using several third-party APIs.
Implement InlineKeyboardMarkup, logging, auto-reconnection.
Place the bot on the linux(raspbian) server using PM2(advanced, production process manager for node.js)

#### What can the bot do?
1. accepts a hex code and generates a colored rectangle with the name of the color
2. generates the following color palettes based on the resulting hex code:
  a) Complementary
  b) Monochromatic
  c) Analogous
  d) Triadic
  e) Tetradic

#### List of supported commands:
    /start
    /help

#### List of used libraries:
1. telegram bots - library to create telegram bots
2. telegram bots extensions - extensions bots for telegram bots library
3. lombok - saves us from boilerplate code
4. log4j - logger

You can use its services yourself if it is online - https://t.me/palette_helper_bot.

If you want to use a bot with your token, run it with the parameters (bot_name bot_token).

I didn't rent a server for a bot, but just used raspberries. This is my little production server from improvised means.
