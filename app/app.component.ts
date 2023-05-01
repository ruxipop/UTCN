import {Component} from '@angular/core';
import {Router} from "@angular/router";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {
    title = 'daw_frontend';

    constructor(private router: Router) {
    }

    isLoginOrRegisterOrRentOrBuyPage(): boolean {
        return this.router.url.includes('/login') || this.router.url.includes('/register') || this.router.url.includes('/buy') || this.router.url.includes('/rent');
    }

}
