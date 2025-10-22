   // ------------------------------------------------
    // Ask Question
    const form = document.getElementById('restForm');
    const responseDiv = document.getElementById('response');
    const responseText = document.getElementById('responseText');
    const requestTime = document.getElementById('requestTime');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        responseText.textContent = "...loading";
        requestTime.textContent = "";

        const identifier = document.getElementById('identifier').value;
        const question = document.getElementById('question').value;

        // Replace with your actual REST API endpoint
        const apiUrl = 'vector/search';

        const payload = { identifier, question };

        //JSON.stringify(payload)
        const startTime = performance.now();
        try {
            const res = await fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: question
            });

            //const data = await res.json();
            const data = await res.text();
            const endTime = performance.now();

            //responseText.textContent = JSON.stringify(data, null, 2);

            responseText.textContent = data;
            requestTime.textContent = (endTime - startTime).toFixed(2);
            responseDiv.style.display = 'block';
        } catch (error) {
            const endTime = performance.now();
            responseText.textContent = `Error: ${error}`;
            requestTime.textContent = (endTime - startTime).toFixed(2);
            responseDiv.style.display = 'block';
        }
    };

    // ------------------------------------------------
    // Answer Question
    const answerForm = document.getElementById('addContextForm');

    answerForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        responseText.textContent = "...loading";
        requestTime.textContent = "";

        const identifier = document.getElementById('identifier').value;
        const question = document.getElementById('question').value;
        const answer = document.getElementById('answer').value;

        const promptContextParam = "prompt="+question+"&context="+answer;

        // Replace with your actual REST API endpoint
        const apiUrl = 'vector/search/prompt/context';

        //const payload = { identifier, question };

        //JSON.stringify(payload)
        const startTime = performance.now();
        try {
            const res = await fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'accept' : '*/*'
                },
                body: promptContextParam
            });

            //const data = await res.json();
            const data = await res.text();
            const endTime = performance.now();

            //responseText.textContent = JSON.stringify(data, null, 2);

            responseText.textContent = data;
            requestTime.textContent = (endTime - startTime).toFixed(2);
            responseDiv.style.display = 'block';
        } catch (error) {
            const endTime = performance.now();
            responseText.textContent = `Error: ${error}`;
            requestTime.textContent = (endTime - startTime).toFixed(2);
            responseDiv.style.display = 'block';
        }
    };
