import {Component, Input} from '@angular/core';
import {PropertyService} from "../../services/property.service";
import {Router} from "@angular/router";


@Component({
    selector: 'app-navigation-bar-submenu',
    templateUrl: './navigation-bar-submenu.component.html',
    styleUrls: ['./navigation-bar-submenu.component.scss']
})
export class NavigationBarSubmenuComponent {

    @Input() url: string = '';

    constructor(private service: PropertyService, private router: Router) {
    }

    goToByFilter(value: string) {
        console.log(this.url)
        if (this.url.includes('rent-page')) {
            this.router.navigateByUrl('rent-page').then(() => {
                localStorage.setItem('value', value)

            });
        } else {

            this.router.navigateByUrl('buy-page').then(() => {
                localStorage.setItem('value', value)

            });
        }
    }
}
