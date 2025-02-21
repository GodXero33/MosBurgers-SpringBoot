(function () {
	async function init () {
		deleteStyleSheet('login');

		try {
			await newStyleSheet('components/content/nav-panel.css', 'nav-panel');
			await newStyleSheet('components/content/content.css', 'content');
			await newStyleSheet('components/content/home.css', 'home');
			await newStyleSheet('components/content/place-order.css', 'place-order');
			await newStyleSheet('components/food-item/item-card.css', 'item-card');
			await newStyleSheet('components/food-item/item-place.css', 'item-place');
			await newStyleSheet('components/food-item/cart.css', 'cart');

			await loadDynamicSrcipt('js/nav-panel.js');
			await loadDynamicSrcipt('js/home.js');
			await loadDynamicSrcipt('js/place-order.js');
		} catch (error) {
			console.error(error);
		}

		SHOP_WINDOW['loader'].classList.add('hide');
	}

	async function createContent () {
		try {
			const response = await fetch('components/content/content.html', { cache: 'no-cache' });

			if (!response.ok) throw new Error('Failed to fetch');

			const html = await response.text();

			setTimeout(() => {
				SHOP_WINDOW['main-container'].innerHTML = html;
				init();
			}, 1);
		} catch (error) {
			console.error(error);
		}
	}

	createContent();
})();
