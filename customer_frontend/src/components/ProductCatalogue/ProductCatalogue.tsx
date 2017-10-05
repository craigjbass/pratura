import * as React from 'react'
import Product from './Product'

export interface PresentableProduct {
    sku: String,
    name: String
}

export interface Props {
    products: PresentableProduct[],
    addToBasket: (request: any) => any
}

export default class ProductCatalogue extends React.Component<Props, object> {
    public render() {
        return (
            <ul>
                {this.getProductComponents().map(
                    (pc, i) => (<li key={i}>{pc}</li>)
                )}
            </ul>
        )
    }

    private getProductComponents() {
        return this.props.products.map((product) => (
            <Product
                addToBasket={this.onAddToBasket}
                sku={product.sku}
                name={product.name}
            />
        ))
    }

    private onAddToBasket = (sku: String) => {
        this.props.addToBasket({ sku })
    }
}
