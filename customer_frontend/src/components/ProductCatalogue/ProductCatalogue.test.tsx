import * as React from 'react'
import * as ReactDOM from 'react-dom'
import { Simulate } from 'react-dom/test-utils'
import ProductCatalogue from './ProductCatalogue'

let render = function (element: any) {
    const div = document.createElement('div')
    ReactDOM.render(element, div)
    return div.children[0]
}

class ProductCatalogueTestHarness {
    private receivedSkus: String[] = []
    private element: Element
    private products: [{ sku: string; name: string }]

    render(): void {
        this.element = render((
            <ProductCatalogue
                products={this.products}
                addToBasket={({ sku }) => this.receivedSkus.push(sku)}
            />
        ))
    }

    getHtml(): String {
        return this.element.innerHTML
    }

    clickAddToCart(sku: String = 'SKU-123456'): void {
        let element = this
            .element
            .querySelector(`[data-unit-test='add-to-basket-${sku}']`)

        if (!element) return
        Simulate.click(element)
    }

    getReceivedSkus(): String[] {
        return this.receivedSkus
    }

    setProducts(products: [{ sku: string; name: string }]) {
        this.products = products
    }
}

describe('<Product />', () => {
    let wrapper: ProductCatalogueTestHarness
    beforeEach(() => wrapper = new ProductCatalogueTestHarness())

    describe('given one product', () => {
        beforeEach(() => {
            wrapper.setProducts([
                { sku: 'SKU-123456', name: 'Fillet o\' Fish' }
            ])
            wrapper.render()
        })

        it('renders the product details', () => {
            expect(wrapper.getHtml()).toContain('Fillet o\' Fish')
            expect(wrapper.getHtml()).toContain('SKU-123456')
        })

        it('sends the add to basket event correctly', () => {
            wrapper.clickAddToCart()

            expect(wrapper.getReceivedSkus().length).toEqual(1)
            expect(wrapper.getReceivedSkus()[0]).toEqual('SKU-123456')
        })
    })

    describe('given two products', () => {
        beforeEach(() => {
            wrapper.setProducts([
                { sku: 'SKU-123456', name: 'Fillet o\' Fish' },
                { sku: 'SKU-912019', name: 'Turkey' }
            ])
            wrapper.render()
        })

        it('renders the product details', () => {
            expect(wrapper.getHtml()).toContain('Fillet o\' Fish')
            expect(wrapper.getHtml()).toContain('Turkey')
            expect(wrapper.getHtml()).toContain('SKU-123456')
            expect(wrapper.getHtml()).toContain('SKU-912019')
        })

        it('sends the add to basket event correctly', () => {
            wrapper.clickAddToCart('SKU-123456')
            wrapper.clickAddToCart('SKU-912019')

            expect(wrapper.getReceivedSkus().length).toEqual(2)
            expect(wrapper.getReceivedSkus()[0]).toEqual('SKU-123456')
            expect(wrapper.getReceivedSkus()[1]).toEqual('SKU-912019')
        })
    })
})
