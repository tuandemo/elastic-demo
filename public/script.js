const baseUrl = 'http://localhost:8080';

function $id(id) {
    return document.getElementById(id);
}

function createDiv(className = '', ...contents) {
    const div = document.createElement('div');
    div.className = className;
    div.append(...contents);
    return div;
}

function search(text, startPrice, endPrice, elastic = false, handler = console.log) {
    fetch(`${baseUrl}/search?elastic=${elastic}`, {
        method: 'POST',
        body: JSON.stringify({
            query: text,
            priceRange: {
                start: startPrice,
                end: endPrice,
            },
        }),
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((res) => res.json()).then(handler)
        .catch((err) => alert(err.message));
}

function element(item = {}) {
    const {
        bicycleName,
        bicycleYear,
        brandName,
        modelName,
        description,
        price,
    } = item;
    const bnDiv = createDiv('bn', bicycleName);
    const byDiv = createDiv('by', bicycleYear);
    const brnDiv = createDiv('brn', brandName);
    const mnDiv = createDiv('mn', modelName);
    const dDiv = createDiv('d', description);
    const pDiv = createDiv('p', price);
    return createDiv('item',
        bnDiv,
        byDiv,
        brnDiv,
        mnDiv,
        dDiv,
        pDiv);
}

function render(items) {
    const searchContainer = $id('search-container');
    const elements = [];
    for (const item of items) {
        elements.push(element(item));
    }
    searchContainer.replaceChildren(...elements);
    if (!items?.length) alert('Not found bro!');
}

{
    const searchBox = $id('search-box');
    const startPriceBox = $id('start-price-box');
    const endPriceBox = $id('end-price-box');
    const checkBox = $id('search-checkbox');

    const onKeyDown = (e) => {
        if (e.key === 'Enter') {
            const text = searchBox.value;
            const checked = checkBox.checked;
            const startPrice = Number(startPriceBox.value) || undefined;
            const endPrice = Number(endPriceBox.value) || undefined;
            search(text, startPrice, endPrice, checked, render);
        }
    };

    searchBox.onkeydown = onKeyDown;
    startPriceBox.onkeydown = onKeyDown;
    endPriceBox.onkeydown = onKeyDown;
}