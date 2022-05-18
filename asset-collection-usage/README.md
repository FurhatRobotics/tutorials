# StandardLibraryUsageExample

The skill demonstrate how to use various functions and feature of the Asset Collection that is part of the Furhat Library, read more in the docs (https://docs.furhat.io/library/). 

This example skill includes an example of using lines and intents defined in Google Sheet pages and tabs to retrieve utterances and log responses. The skill also demonstrates various  
 utility functions and gestures coming from the Asset Collection (StandardLibraryCollection). Read more about the Asset Collection at https://docs.furhat.io/library/#asset-collection

To view the source code of a class, object or function from the Asset Collection ((StandardLibraryCollection) right click and select 'Go to - Declaration or usages' (Ctrl-b / Cmd-b)

## Functionalities of the skill

This skill does not pretend to have a special utility, but is to be considered as an example of how to use the main AssetCollection tools.

The usages that can be made are the following :
* Greet users with examples from a Google Sheet, that may differ if there is a small user in the audience
* Demonstrate the reaction to different (non) existing lines from the Google Sheet
* Answers to intents from Google Sheets
* Have a Sleep Mode that is triggered on `IAmDone` answers, only to wake up if asked by the user
* Show different built-in expressions
