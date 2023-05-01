import {Component} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
    selector: 'app-navigation-bar',
    templateUrl: './navigation-bar.component.html',
    styleUrls: ['./navigation-bar.component.scss']
})
export class NavigationBarComponent {
    showContainer = false;
    showRentContainer = false;
    isButtonHighlighted = false;

//@ts-ignore
    isLoggedIn: boolean;

    constructor(private http: HttpClient, private router: Router, public authService: AuthService) {

        this.authService.isLoggedIn.subscribe(isLoggedIn => {
            this.isLoggedIn = isLoggedIn;
        });
    }

    hideSubmenu() {
        this.showContainer = false;
        this.isButtonHighlighted = false;
    }

    onBuyButtonHover() {
        this.showContainer = true;
        this.isButtonHighlighted = true;
        document.addEventListener('mouseleave', this.hideSubmenu.bind(this));
    }

    onSubmenuHover() {
        document.removeEventListener('mouseleave', this.hideSubmenu.bind(this));
    }

    onSubmenuLeave() {
        this.hideSubmenu();
    }

    onBuyButtonLeave() {
        setTimeout(() => {
            if (!this.showContainer) {
                this.hideSubmenu();
            }
        }, 200);
        document.removeEventListener('mouseleave', this.hideSubmenu.bind(this));
        this.isButtonHighlighted = false;
    }

    onRentButtonHover() {
        this.showRentContainer = true;
    }

    onRentButtonLeave() {

        setTimeout(() => {
            if (this.showRentContainer) {
                this.showRentContainer = false;
            }
        }, 1000);


    }

    myFunction() {
        const x = document.getElementById('myTopnav');
        // @ts-ignore
        if (x.className === 'topnav') {
            // @ts-ignore
            x.className += ' responsive';
        } else {
            // @ts-ignore
            x.className = 'topnav';
        }
    }

    contactButton() {
        this.router.navigateByUrl("contact-page").then(r => null)
    }

    aboutButton() {
        this.router.navigateByUrl("about-page").then(r => null)
    }

    goToBuy() {
        localStorage.setItem('value', 'buy')
        this.router.navigateByUrl('buy-page').then(r => null)
    }

    goToRent() {
        localStorage.setItem('value', 'rent')
        this.router.navigateByUrl('rent-page').then(r => null)
    }
}

