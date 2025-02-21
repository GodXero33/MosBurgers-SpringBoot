(function () {
	const itemsHolder = document.getElementById('place-order-items-holder');
	const itemPlaceContainer = document.getElementById('item-place-card');
	const itemPlaceContainerCloseBtn = document.getElementById('item-place-card-close-btn');
	const itemPlaceContainerBackdrop = itemPlaceContainer.querySelector('.backdrop');
	const itemPlaceCardContainer = document.getElementById('item-place-card-holder');
	const itemPlaceCountInput = document.getElementById('item-place-count-input');
	const itemPlacePlaceBtn = document.getElementById('item-place-place-btn');
	const cartContainer = document.getElementById('cart-container');
	const cartCloseBtn = document.getElementById('cart-close-btn');
	const cartOpenBtn = document.getElementById('open-cart-btn');
	const cartPlaceBtn = document.getElementById('cart-place-btn');
	const cartHolder = document.getElementById('cart-holder');
	const placeOrderFoodFilter = document.getElementById('place-order-food-filter');
	const placeOrderFoodSearch = document.getElementById('place-order-food-search');
	const customerSelectContainer = document.getElementById('customer-select-container');
	const customerSelectActionBtn = document.getElementById('customer-select-action-btn');
	const customerSelectCloseBtn = document.getElementById('customer-select-close-btn');
	const customerSelectInputs = Array.from(customerSelectContainer.querySelectorAll('.customer-select-input'));
	
	let items = null;
	let placeOrderCardComponent = null;
	let itemPlaceComponent = null;
	let cartComponent = null;
	let cartItemComponent = null;
	let currentPlaceItemIndex = -1;
	let currentPlaceItemCard = null;
	let isOrderPlacing = false;
	let currentFoodFilterState = 'All';
	const cart = {
		items: [],
		price: 0,
		discount: 0,
		total: 0,
		customer: -1
	};

	function placeOrder () {
		return new Promise(async (res, rej) => {
			isOrderPlacing = true;
	
			const customer_id = cart.customer;
			const admin_id = SHOP_WINDOW['admin'].admin_id;
			const today = new Date();
			const place_date = `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`;
			const total_amount = cart.total;
			const discount = cart.discount;
			const final_amount = cart.total;
			
			const order = {
				customer_id,
				admin_id,
				place_date,
				total_amount,
				discount,
				final_amount,
				items: cart.items.map(cartItem => {
					const item_id = cartItem.item.item_id;
					const quantity = cartItem.count;
					const total_price = cartItem.total;
					const price_per_unit = cartItem.item.price;
	
					return {
						item_id,
						quantity,
						total_price,
						price_per_unit
					};
				})
			};
	
			try {
				const response = await fetch(`${SHOP_WINDOW.db_host}/order`, {
					method: 'POST',
					body: JSON.stringify(order),
					headers: {
						'Content-Type': 'application/json'
					}
				});
	
				if (!response.ok) throw new Error('Somthing went wrong while placing order.');
	
				const data = await response.json();
				isOrderPlacing = false;
				
				if (data.ok) {
					hideCartCard();
					resetCart();
					sendInfoAlert('Order Placed!');
					res();
				} else {
					sendInfoAlert('Order Failed!');
					rej();
				}
			} catch (error) {
				console.log(error);
				rej();
			}
		});
	}

	function hideCartCard () {
		cartContainer.classList.remove('show');
	}

	function openCartCard () {
		cartContainer.classList.add('show');

		let cartItemsHTML = '';

		cart.items.forEach(cartItem => {
			itemHTML = cartItemComponent
				.replace(':NAME', cartItem.item.name)
				.replace(':QUANTITY', cartItem.count)
				.replace(':PRICE', cartItem.price.toFixed(2))
				.replace(':DISCOUNT', cartItem.discount.toFixed(2))
				.replace(':TOTAL', cartItem.total.toFixed(2));
			
			cartItemsHTML += itemHTML;
		});

		cartHolder.innerHTML = cartComponent
			.replace(':ITEMS', cartItemsHTML)
			.replace(':PRICE', cart.price.toFixed(2))
			.replace(':DISCOUNT', cart.discount.toFixed(2))
			.replace(':TOTAL', cart.total.toFixed(2));
	}

	function resetCart () {
		cart.items = [],
		cart.price = 0;
		cart.discount = 0;
		cart.total = 0;

		itemsHolder.querySelectorAll('.item-card').forEach(card => {
			if (card.classList.contains('active')) card.classList.remove('active');
		});
	}

	function updateCart () {
		let price = 0;
		let discount = 0;
		let total = 0;

		cart.items.forEach(cartItem => {
			price += cartItem.price;
			discount += cartItem.discount;
			total += cartItem.total;
		});

		cart.price = price;
		cart.discount = discount;
		cart.total = total;
	}

	function addPlaceItemToCart () {
		const item = items.find(item => item['item_id'] == currentPlaceItemIndex);

		if (!item) return;

		const existItemIndex = cart.items.findIndex(cartItem => cartItem.item.code == item.code);
		const count = itemPlaceCountInput.value;

		if (itemPlaceCountInput.value == 0) {
			if (existItemIndex == -1) {
				sendWarningAlert('Qunatity is 0.');
				return;
			}

			cart.items.splice(existItemIndex, 1);
			currentPlaceItemCard.classList.remove('active');
			hideItemPlaceCard();
			updateCart();
			return;
		}
	
		const existItem = cart.items[existItemIndex];

		if (existItem) {
			const price = item.price * count;
			const discount = item.discount * count;
			const total = price - discount;

			existItem.count = count;
			existItem.price = price;
			existItem.discount = discount;
			existItem.total = total;
		} else {
			const price = item.price * count;
			const discount = item.discount * count;
			const total = price - discount;

			cart.items.push({
				item,
				count,
				price,
				discount,
				total
			});
		}

		currentPlaceItemCard.classList.add('active');
		hideItemPlaceCard();
		updateCart();
	}

	function hideItemPlaceCard () {
		itemPlaceContainer.classList.remove('show');
		itemPlaceCountInput.value = 0;
		currentPlaceItemIndex = -1;
		currentPlaceItemCard = null;
	}

	function showItemPlaceCard () {
		itemPlaceContainer.classList.add('show');
		const item = items.find(item => item['item_id'] == currentPlaceItemIndex);

		if (!item) return;

		const existItem = cart.items.find(cartItem => cartItem.item['item_id'] == item['item_id']);

		if (existItem) {
			itemPlaceCountInput.value = existItem.count;
		}
	}

	function updateItemPlaceCard () {
		const item = items.find(item => item['item_id'] == currentPlaceItemIndex);

		if (!item) return;

		const count = itemPlaceCountInput.value;
		const price = item.price * count;
		const discount = item.discount * count;
		const total = price - discount;
		const { name, category, code } = item;

		const holderHTML = itemPlaceComponent
			.replace(':NAME', name)
			.replace(':CATEGORY', category)
			.replace(':PRICE', price.toFixed(2))
			.replace(':DISCOUNT', discount.toFixed(2))
			.replace(':TOTAL', total.toFixed(2))
			.replace(':IMAGE', code);
		
		itemPlaceCardContainer.innerHTML = holderHTML;
	}

	function createFoodCards () {
		items.forEach(item => {
			const card = placeOrderCardComponent
				.replace(':PRICE', item['price'])
				.replace(':DISCOUNT', item['discount'])
				.replace(':TITLE', item['name'])
				.replace(':IMAGE', item['code'])
				.replace(':INDEX', item['item_id']);
			const parser = new DOMParser();
			const doc = parser.parseFromString(card, 'text/html');
			const cardDOM = doc.querySelector('.item-card');

			item.cardDOM = cardDOM;
			itemsHolder.appendChild(cardDOM);
		});
	}

	function filterFoodCardsByCategory (category) {
		itemsHolder.innerHTML = '';
		items.filter(item => category == 'All' || item['category'] == category).forEach(item => itemsHolder.appendChild(item['cardDOM']));
	}

	function filterFoodCardsByName (name) {
		itemsHolder.innerHTML = '';
		name = name.trim();
		items.filter(item => item['name'].toLowerCase().includes(name.toLowerCase())).forEach(item => itemsHolder.appendChild(item['cardDOM']));
	}

	function checkCustomerIsAlreadyLogged (id) {
		return new Promise(async (res, rej) => {
			try {
				const response = await fetch(`${SHOP_WINDOW['db_host']}/customers/${id}`);

				if (!response.ok) throw new Error('Failed to fetch customer search.');

				const customer = await response.json();
				res(customer);
			} catch (error) {
				console.error(error);
				res(null);
			}
		});
	}

	function addNewCustomer (customer) {
		return new Promise(async (res, rej) => {
			try {
				const response = await fetch(`${SHOP_WINDOW['db_host']}/customers`, {
					method: 'POST',
					body: JSON.stringify(customer),
					headers: {
						'Content-Type': 'application/json'
					}
				});

				if (!response.ok) throw new Error('Failed to fetch add new customer.');

				const newCustomer = await response.json();
				res(newCustomer);
			} catch (error) {
				console.error(error);
				res(null);
			}
		});
	}

	function resetCustomerSelectContainer () {
		customerSelectInputs.forEach((input, index) => {
			input.disabled = index != 0;
			input.value = '';
		});

		customerSelectActionBtn.disabled = false;
		customerSelectActionBtn.classList.remove('add');
		customerSelectActionBtn.classList.remove('place');
		customerSelectContainer.classList.remove('show');
	}

	async function customerSelectActionBtnAddCustomer () {
		const values = customerSelectInputs.map(input => input.value);
		
		if (!/^([a-zA-Z]{3,})( [a-zA-Z]{3,})*$/.test(values[1])) {
			sendWarningAlert('Invalid name!');
			return;
		}

		if (values[2] != '' && !/^07(\d{8})$/.test(values[2])) {
			sendWarningAlert('Invalid phone!');
			return;
		}

		if (values[3] != '' && !/^([a-z0-9]+)@([a-z]*)mail.([a-z]+)$/.test(values[3])) {
			sendWarningAlert('Invalid email!');
			return;
		}

		customerSelectActionBtn.disabled = true;

		const customer = {
			name: values[1],
			phone: values[2],
			email: values[3],
			address: values[4]
		};

		try {
			const newCustomer = await addNewCustomer(customer);
			
			if (newCustomer) {
				customerSelectInputs[0].value = newCustomer['customer_id'];
				cart.customer = newCustomer['customer_id'];
				sendInfoAlert('New Customer ID is ' + newCustomer['customer_id']);

				customerSelectInputs.forEach(input => input.disabled = true);

				customerSelectActionBtn.classList.remove('add');
				customerSelectActionBtn.classList.add('place');
				customerSelectActionBtn.disabled = false;
			} else {
				sendWarningAlert('Failed to add new Customer. Please try again!');
			}
		} catch (error) {
			console.error(error);
		}

		customerSelectActionBtn.disabled = false;
	}

	async function customerSelectActionBtnAction () {
		if (customerSelectActionBtn.classList.contains('add')) {
			customerSelectActionBtnAddCustomer();
			return;
		}

		if (customerSelectActionBtn.classList.contains('place')) {
			customerSelectActionBtn.disabled = true;
			await placeOrder();
			resetCustomerSelectContainer();
			return;
		}

		if (customerSelectInputs[0].value * 1 < 1) {
			sendWarningAlert('Customer ID is invlid!');
			return;
		}

		customerSelectInputs[0].disabled = customerSelectActionBtn.disabled = true;

		try {
			const loggedCustomer = await checkCustomerIsAlreadyLogged(customerSelectInputs[0].value * 1);
			
			if (loggedCustomer) {
				customerSelectInputs[1].value = loggedCustomer['name'];
				customerSelectInputs[2].value = loggedCustomer['phone'];
				customerSelectInputs[3].value = loggedCustomer['email'];
				customerSelectInputs[4].value = loggedCustomer['address'];
				cart.customer = loggedCustomer['customer_id'];
				customerSelectActionBtn.classList.add('place');
				customerSelectActionBtn.disabled = false;
			} else {
				sendWarningAlert('customer is not found. Please add new customer!');
				customerSelectActionBtn.disabled = false;
				customerSelectInputs[0].value = '';

				for (let i = 1; i < customerSelectInputs.length; i++) customerSelectInputs[i].disabled = false;

				customerSelectActionBtn.classList.add('add');
			}
		} catch (error) {
			resetCustomerSelectContainer();
			console.error(error);
		}
	}

	async function loadItems () {
		try {
			const response = await fetch(`${SHOP_WINDOW.db_host}/items`);

			if (!response.ok) throw new Error('Failed to fetch items data.');

			items = await response.json();
		} catch (error) {
			console.error(error);
		}
	}

	async function loadComponent (component) {
		try {
			const response = await fetch(`components/food-item/${component}.html`);

			if (!response.ok) throw new Error(`Failed to fetch ${component} component.`);

			const componentHTML = await response.text();
			return componentHTML;
		} catch (error) {
			console.error(error);
		}
	}

	async function loadFoodResources () {
		await loadItems();
		placeOrderCardComponent = await loadComponent('item-card');
		itemPlaceComponent = await loadComponent('item-place');
		cartComponent = await loadComponent('cart');
		cartItemComponent = await loadComponent('cart-item');

		createFoodCards();

		document.getElementById('place-order-section').addEventListener('click', (event) => {
			if (event.target == itemPlaceContainerCloseBtn || event.target == itemPlaceContainerBackdrop) {
				hideItemPlaceCard();
				return;
			}

			if (event.target == cartCloseBtn) {
				if (isOrderPlacing) {
					sendWarningAlert('The order is still placing. Please wait!');
					return;
				}

				hideCartCard();
				return;
			}

			if (event.target == cartOpenBtn) {
				openCartCard();
				return;
			}

			if (event.target == cartPlaceBtn) {
				if (cart.items.length == 0) {
					sendWarningAlert('The cart is Empty. Can\'t place an order. Please add somthing into the cart.');
					return;
				}

				customerSelectContainer.classList.add('show');
				return;
			}

			if (event.target == customerSelectActionBtn) {
				customerSelectActionBtnAction();
				return;
			}

			if (event.target == customerSelectCloseBtn) {
				resetCustomerSelectContainer();
				return;
			}

			const card = event.target.closest('.item-card');
	
			if (!card) return;
	
			currentPlaceItemIndex = card.dataset.index * 1;
			currentPlaceItemCard = card;
			showItemPlaceCard();
			updateItemPlaceCard();
		});

		itemPlacePlaceBtn.addEventListener('click', addPlaceItemToCart);
		itemPlaceCountInput.addEventListener('input', updateItemPlaceCard);
		placeOrderFoodFilter.addEventListener('change', () => {
			if (currentFoodFilterState == placeOrderFoodFilter.value) return;

			currentFoodFilterState = placeOrderFoodFilter.value;
			filterFoodCardsByCategory(currentFoodFilterState);
		});
		placeOrderFoodSearch.addEventListener('input', () => {
			filterFoodCardsByName(placeOrderFoodSearch.value);
		});
	}

	loadFoodResources();
})();
