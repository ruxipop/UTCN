
import {Component} from '@angular/core';
import {PropertyService} from "../services/property.service";
import {Property} from "../models/property";
import {Router} from "@angular/router";
import {DialogContentComponent} from "../dialog-content/dialog-content.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
    selector: 'app-buy-page',
    templateUrl: './buy-page.component.html',
    styleUrls: ['./buy-page.component.scss']
})
export class BuyPageComponent {

    isResidentialSelected = false;
    isCommercialSelected = false;
    value: string = '';
    staticProperties: Property[] = [];
    properties: Property[] = [];
    selectedCheckboxes: number = 0;
    currentUsername: string = '';
    favProperties: Property[] = []


    constructor(private service: PropertyService, private router: Router, private dialog: MatDialog) {
        //@ts-ignore
        this.currentUsername = localStorage.getItem('username')
        //@ts-ignore
        this.getFavProperties();
        this.service.getProperties().subscribe(data => {
            this.properties = data.properties.filter((prop) => !prop.isBuy);
            this.staticProperties = this.properties;
            // @ts-ignore
            this.value = localStorage.getItem('value')
            if (this.value) {
                this.filterByValue(this.value)
            }
        });
    }

    isAdmin() {
        // @ts-ignore
        return parseInt(localStorage.getItem('role')) === 0;
    }

    getFavProperties() {
        if (this.currentUsername != null) {
            this.service.getUserFavProperties().subscribe((res) =>
                // @ts-ignore
                this.favProperties = res.filter((prop) => !prop.isBuy)
            );
        }
    }

    toggleFilter() {
        this.selectedCheckboxes = this.getNumberOfSelectedCheckboxes();
        const filterMenu = document.querySelector('.filter-menu');
        // @ts-ignore
        filterMenu.style.display = filterMenu.style.display === 'block' ? 'none' : 'block';
    }

    getNumberOfSelectedCheckboxes(): number {
        let numberOfSelectedCheckboxes = 0;
        const checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');

        checkboxes.forEach((checkbox) => {
            numberOfSelectedCheckboxes++;
        });

        return numberOfSelectedCheckboxes;
    }

    filterByValue(value: string) {
        console.log(value)
        if (value === 'Commercial') {
            this.getCommercial();
        } else if (value === 'Residential') {
            this.getResidential()
        } else if (value != 'buy') {
            // @ts-ignore
            document.querySelector(`input[type="checkbox"][value="${value}"]`).checked = true;
            this.toggleFilter()
            this.properties = this.staticProperties.filter(p => [value].includes(p.type))
        }
    }

    applyFilters() {
        // @ts-ignore
        const types = Array.from(document.querySelectorAll('input[name="types"]:checked')).map(el => el.value);
        this.properties = (types.length === 0) ? this.staticProperties : this.staticProperties.filter(property => types.includes(property.type));
        this.toggleFilter();
    }

    openPropertyPage(id: number) {
        if (!this.isUserLogged()) {
            //@ts-ignore
            this.service.setCurrentProperty(this.properties.find(p => p.id === id));
            localStorage.setItem('selected', String(id))
            this.router.navigate(['/selected', id]).then(r => null);
        }
    }

    deselectCheck() {
        const checkboxes = document.querySelectorAll('input[name="types"]:checked');
        checkboxes.forEach(checkbox => {
            //@ts-ignore
            checkbox.checked = false;
        });
        this.selectedCheckboxes = 0;
    }

    getResidential() {
        this.selectedCheckboxes = 0;
        if (this.isCommercialSelected) {

            this.isCommercialSelected = false;
        }
        this.isResidentialSelected = !this.isResidentialSelected;
        this.properties = this.isResidentialSelected ? this.staticProperties.filter(p => ['Apartment', 'Studio', 'House'].includes(p.type)) : this.staticProperties;
        this.deselectCheck()
    }

    getCommercial() {
        console.log(this.favProperties)
        if (this.isResidentialSelected) {
            this.isResidentialSelected = false;
        }
        this.isCommercialSelected = !this.isCommercialSelected;
        this.properties = this.isCommercialSelected ? this.staticProperties.filter(p => ['Office', 'Space', 'Hotel'].includes(p.type)) : this.staticProperties;
        this.deselectCheck()
    }

    addToFav(property: Property) {
        //@ts-ignore
        if (this.isFav(property.id)) {
            this.favProperties = this.favProperties.filter(element => element.id !== property.id);
            //@ts-ignore
            this.service.deleteFavProperty(property.id).subscribe()

        } else {
            this.favProperties.push(property)
            this.favProperties = [...this.favProperties];
            //@ts-ignore
            this.service.addFavProp(property.id).subscribe()
            //@ts-ignore
            let heart = document.getElementById(property.id).querySelector('.fav');
            //@ts-ignore
            heart.classList.add('selected')
        }
    }

    isFav(id: number) {
        return this.favProperties.some(prop => prop.id === id);
    }

    isUserLogged() {
        return this.currentUsername === null;
    }

    deleteProperty(propertyId: number) {
        this.properties = this.properties.filter(element => element.id !== propertyId);
        this.service.deleteProperty(propertyId).subscribe();
    }

    openDialog(id: number) {
        this.dialog.open(DialogContentComponent, {
            width: '500px',
            data: {id: id}
        });


    }
}