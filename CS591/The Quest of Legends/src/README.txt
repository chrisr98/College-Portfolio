Chris-Emio Raymond
U20803007

How to use program:
The following will compile and execute the code provided all files are in the same directory
"javac RunGame.java && java RunGame"

The 8x8 board looks like this
[ _ _ _ ]
[ _ _ _ ]
[ _ _ _ ]

***RULES OF THE GAME***
Inputs are W,A,S,D. 
There are 3 types of tiles, when the game begins the tiles are randomly set. 
First, is the common tile denoted as a subbar '_'. Those tiles are for free to move onto but will always trigger an encounter against a group of random enemies. You will have a chance to see the enemies and decide whther or not you want to run away from the encounter. 
Second, is the market tile denoted as an m 'M'. Those tiles are for free to move onto but will always open up the market for you to buy items such as weapons, armor, potion, and spells. 
Third, is unaccessible tile denoted as an ampersand '&'. Those tiles are considered walls as you cannot stepp on to them and will block your path forcing you to find your way around. 
Game will end when player loses or quits.

Items in the market have level requirement and will tell you when you cannot buy said item. 
There are 6 potions, health, strength magic, luck, mermaid tears, and ambrosia. Each potion will increase their corresponding stats by a flat amount.
*List of potions*
Health  is for HP
Str is for str
Magic is for mana
Luck is for agility
Mermaid is for dex
Ambrosia is for exp

The game will prompt user for input whenever necessary and will evaluate the input.

***DECISIONS ON IMPLEMENTATION****
Common tiles always trigger encounter because moving around the map to find an encounter is boring. You have the option to leave the battle every round. 
I've decided to not let the user quit the game if they are in a fight or in the market. If the fight is tough the player can run away and not quit the game. I've decided to add a penalty because there is no shame for running.
Enemies are randomly chosen from the 3 types. There will always be at least 1 enemy that is the same or higher level as the highest level among the heroes.
Each hero has their own wallets and spells. Can see all of them in the info menu and choose which one to access. 
I changed heroes to remove duplicated names, there is now 4 unique heroes per class.
In the market, the player can always buy health potions. But aside from that there is only 1 item/per type randomly chosen from the list for sale. 
You can constantly re-roll to get an a better items which means players can't just get the best item to win the game. They need to strategize how and when to spend their money.
I've changed some of the numbers a bit, so players stats increase by 10% rather than 5% when they level up and so on. Just to so the game isn't too hard to play. Items are infinite will show if lucky enough but assume they will never run out of a specific item. 



Class Description:
