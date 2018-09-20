const express = require('express');
const app = express();
const bodyParser = require('body-parser');

const omise = require('omise')({
	'publicKey': process.env.OMISE_PKEY,
	'secretKey': process.env.OMISE_SKEY,
});
const hostname = '10.18.201.85'; // PUT YOUR IP ADDRESS HERE
const port = 3000;

/*
 * List of charities
 */
const charities = [
	{ "id": 0, "name": "Ban Khru Noi", "logo_url": "http://rkdretailiq.com/news/img-corporate-baankrunoi.jpg" },
	{ "id": 1, "name": "Habitat for Humanity Thailand", "logo_url": "http://www.adamandlianne.com/uploads/2/2/1/6/2216267/3231127.gif" },
	{ "id": 2, "name": "Paper Ranger", "logo_url": "https://myfreezer.files.wordpress.com/2007/06/paperranger.jpg" },
	{ "id": 3, "name": "Makhampom", "logo_url": "http://www.makhampom.net/makhampom/ppcms/uploads/UserFiles/Image/Thai/T14Publice/2554/January/Newyear/logoweb.jpg" }
];

app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json({ type: 'application/*+json' }))

app.listen(port, hostname, () => {
	console.log(`Server running at http://${hostname}:${port}/`);
});

/*
* This endpoint returns a JSON list of charities.
*/
app.get('/charities', (req, res) => {
	res.setHeader('Content-Type', 'application/json');
	res.status(200)
		.send(JSON.stringify(charities));
});

/*
* This endpoint creates a charge using the supplied token against the Omise API.
*/
app.post('/donations', (req, res) => {
	console.log(req.body);

	const amount = req.body.amount;
	const token = req.body.token;

	omise.charges.create({
		'amount': amount,
		'currency': 'thb',
		'card': token
	}, (err, charge) => {
		if (err) {
			console.log("Transaction ERROR");
			res.status(400).send(`ERROR:  ${err}`);
		}
		else {
			console.log("Transaction SUCCESS");
			res.status(200).send('SUCCESS');
		}
	});
});
