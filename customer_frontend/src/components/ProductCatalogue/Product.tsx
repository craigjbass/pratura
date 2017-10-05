import * as React from 'react'
import { MouseEvent } from 'react'
import './Product.css'

export interface Props {
    addToBasket: (sku: String) => void,
    sku: String,
    name: String
}

export default class Product extends React.Component<Props, object> {
    render() {
        return (
            <div>
                <div>
                    {this.props.name}
                    <small>({this.props.sku})</small>
                </div>
                <a
                    href="#"
                    data-unit-test={`add-to-basket-${this.props.sku}`}
                    onClick={this.onAddToBasket}
                >
                    Add to basket
                </a>
            </div>
        )
    }

    private onAddToBasket = (event: MouseEvent<HTMLAnchorElement>) => {
        event.preventDefault()
        this.props.addToBasket(this.props.sku)
    }
}
