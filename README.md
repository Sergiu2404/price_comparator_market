# Market Price Comparator

## Overview

This project is a console based Java app that helps users:
- monitor product prices and discounts across multiple stores
- optimize their shopping basket for savings
- track newly added discounts
- view historical price trends
- get recommendations based on best value per unit (price per kg, liter, etc.).

# Usage
-Prerequisites: Java 17 + Maven
Clone repo, run program
Choose an option (enter number in range 0 to 7):
1. Get optimized shopping list (only currently available discounts) 
	-opens a submenu (input continuously product names as they are called in the data files to create your desired basket, when you want to stop, input "0")
2. Get list of products with highest discounts across all tracked stores (only those currently available)
	-to see biggest discount percentages for each store
3. Find discounts newly announced / posted (today)
	-to see all discounts from files added today
4. Get price history data points for given product name (filterable data)
	-to see all tracked records for a specific product along with the dates
5. See recommended products (product name, category) considering available discount and value / unit
	-search products that match your give substring for product name / category to see a recommendation after they were sorted by the price per unit
	-if input is empty for one of them, it filters only based on the other that is nonempty, if both nonempty, all are retrieved
6. See all tracked products
	-see all products loaded from the files
7. See all discounts / all available discounts
	-opens submenu: 1. see all loaded discounts from file;
			2. see all currently available discounts only;
			0. to exit this submenu and go back to the main menu
0. Exit the program



Project's structure is as follows:
-data_transfer_onjects/ProductPriceWithDiscount.java # for transfering and presenting enriched data

-domain/
	Product.java # a tracked product
	PriceEntry.java # a price record of a product at a given date and store
	Discount.java # a discount associated with a product

-services/ # business logic
	ProductsPriceService.java # handles products and price entries logic
	DiscountsService.java # handles discounts related logic
	ProductRecommendationService.java # handles logic for recommendation feature
	DailyShoppingBasketService.java # handles logic for distributing shopping lists in most efficient way (cheapest)

-helpers/
	UnitPriceCalculator.java # for converting between units
	DiscountAvailabilityFilterStrategy # part of strategy pattern to help with filtering (keep only available discounts according to current day)
	SubstringPriceEntryStrategy # part of strategy pattern to help with filtering (keep only price entries that contain the given substrings)

-ui/
	UI.java # user interface / cli

-test/ # tests for all functionalities