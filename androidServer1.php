<?php



/*
// This is a simple quotation server. You of course will want to do something slightly more
// interesting, but hey, this isn't bad.
*/

header(''); // send an empty header not to mess with stuff.

// We start with a basic API key. Assuming there isn't a user authentication
// element to the app, this is usually sufficient, although I would highly
// recommend using something like HTTPS to create some sort of security.

if($_POST['APIKey'] != "ultraQuote")
{
	die('unautherized access bro.');
}

$_POST['payload'] = stripslashes($_POST['payload']); //Required if you have magic_quotes on.
$payload = $_POST['payload'];

$payloadObj = json_decode($payload);

switch($payloadObj->action)
{
	case "getQuote":

		$quoteArray = getQuotes();
		$quoteCount = count($quoteArray['quote']);

		$selectedQuote = rand(0, $quoteCount-1);

		$outputArray =
		Array("success" => true,
	        	"quote" =>
				Array(
					Array("quote"=> $quoteArray['quote'][$selectedQuote],
					      "author" => $quoteArray['author'][$selectedQuote])));

		echo json_encode($outputArray);
	break;
}
function getQuotes()
{
	$handle = fopen("quotes.txt", "r");

	$quotes = Array();
	$authors = Array();

	while( ($line = fgets($handle)) !== false )
	{
		$fileParts = explode("|", $line);
		if(count($fileParts) == 2)
		{
			$quotes[] = $fileParts[0];
			$authors[] = trim($fileParts[1]);
		}

	}

	return Array("quote" => $quotes, "author" => $authors);

}

?>