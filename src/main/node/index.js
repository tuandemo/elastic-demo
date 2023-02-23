const express = require('express');

/* Start a file server listening on port 3000. */ {
    const app = express();
    app.use(express.static('public'));

    app.get('/', (_, res) => {
        res.sendFile('index.html');
    });

    app.listen(3000, () => {
        console.log('Listening at http://localhost:3000...');
    });
}