# CSSE 376 Project

Contributors: Andrew White, Joseph Hegg, Tyler Bath
Game: RISK


Our team has used Boundary Value Analysis (BVA) to accurately etch out what edge cases and general cases we will have to test to ensure a properly working replication of the classic board game Risk. Once all of these cases have been tested and are correctly working (in conjunction with one another in a game-like format) our game will be considered “done”.


Rules source: http://www.ultraboardgames.com/risk/game-rules.php


* Beginning moves: !23
   * Players begin to choose countries
   * To start each player rolls a die and the one with the highest roll gets to choose first ( if a tie occurs for the highest roll, the players that tied will reroll until there is one winner )
   * The player that won the die roll will choose a country and put one of his/her armies on that spot. This will continue from highest roller to lowest roller until each player has chosen a country. Then begin the cycle again, until there are no empty countries left.
   * Finally, the highest roller takes his turn first.

* Start of game picking countries: !4
   * Player picks an occupied country (return false if not all picked ie. # placed < 42)
   * Player picks unoccupied country (return true, valid pick)
   * All countries filled picks their own country (return true, valid to add an army)
   * All countries filled picks others country (return false, invalid to place)
   * Number of armies given to each player (determined by # of players)
      * 2 players (40)
      * 3 players (35)
      * 4 players (30)
      * 5 players (25)
      * 6 players (20)

* For attacks between players: !43, !16
   * Attacker higher roll (defender loses)
   * Defender higher roll (attacker loses)
   * Same roll (attacker loses)
   * Number of armies that a player can move in after a victory
      * Rolled 1 die (at least 1 so return 1)
      * Rolled 2 die (at least 2 so return 2)
      * Rolled 3 die (at least 3 so return 3)
   * Multiple dice rules:
      * If one player has multiple dice and the other only has one, the highest dice from each roll are compared
      * If both players have multiple dice, then the highest rolls from each player are compared, the second highest rolls are compared and so on. For each win, a player loses one army, for each loss, a player loses one army.
      * In all comparisons of two dice, if the players have the same roll, the defender wins.

* Checking if countries border each other for valid attack: !23, !16
   * Are bordering (return true)
   * Are not bordering (return false)
   * The same country chosen twice (return false)
   * Both countries are owned by the same player (return false)

* Check if valid armies in country to attack from: !23, !16
   * Player has 1 army in country they are attacking from
      * Attack is not valid (return false)
   * Player has 2 or more armies
      * Player can attack the country (return true)

* Moving armies after attacks (player selects 2 adjacent countries they control): !23, !18, !15
   * Countries border each other (return true, valid move)
   * Countries do not border (return false, not a valid move to fortify)
   * Same country chosen (return false, not valid)
   * Both countries owned by different players (return false, not valid)

* Turning in cards stage (beginning step): !51
   * If a player currently has 5 or more cards at the beginning of their turn they must turn a set of them in (a valid set will always happen at this point).
      * Player has less than 3 cards (do not ask player for a set)
      * Player has 3 or 4 cards (ask player for choice)
      * Player has 5 or more cards (force a turn in from player)
   * Check the current total sets turned in. Every set after 6 total grants an additional +5 armies
      * 0 sets turned in already (player gets 4 armies for first set)
      * 5 set turned in already (player turning in 6th so they get 15)
      * 6 sets turned in already (player turning in 7th so they get 20)

* Check valid card combination: !51
   * Check if the many combinations of cards are sufficient to allow for a set turn in
   * Check submissions of sets of 3 cards by player if valid, in case it is exchange for armies, else reprompt for valid set. If none is given, user denies and game continues.
      * Check for all 3 card combos in testing (return true if valid, false if not)

* Player controls all countries in a continent: !23
   * Check if every country is owned by a player, depending on continent additional armies gained per turn can range from 7 to 2
      * Player controls 0 (get no additional armies)
      * Player controls all but 1 (get no additional armies)
      * Player controls all countries in a continent (player get armies based on country)

* End of turn drawing cards: !44
   * Successful attack performed
      * Having performed on (give player a card, return true)
      * Did not complete a successful attack (no card, return false)
      * Did not attack (return false)

* Taking a card from the deck: !45
   * Deck is empty (need to shuffle discard deck and then return card)
   * Deck is not empty (return top card)

* Game won? (Remove the player from the game): !6
   * More than 1 player left (game continues, return false)
   * Only 1 player left (game is over, return true)

* Player loses and All current cards owned by player are put in discard deck: !35, !32
   * Discard deck size is 0 (size increases)
   * Discard deck size is more than 0 (size increases)
