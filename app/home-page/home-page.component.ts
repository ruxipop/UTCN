import {Component} from '@angular/core';
import {PropertyService} from "../services/property.service";
import {Property} from "../models/property";
import {Router} from "@angular/router";

@Component({
    selector: 'app-home-page',
    templateUrl: './home-page.component.html',
    styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent {

    videoSource = "assets/imgs/daw-video.mp4";
    newProperties: Property[] = [];

    constructor(private service: PropertyService, private router: Router) {
        this.service.getProperties().subscribe((property) =>
            this.newProperties = property.properties.filter((element) => element.isNew)
        )
    }

    openPropertyPage(id: number) {
        //@ts-ignore
        this.service.setCurrentProperty(this.newProperties.find(p => p.id === id));
        this.router.navigate(['/selected', id]).then(r => null);
    }

    goToRegisterPage() {
        this.router.navigateByUrl('register-page')
    }


}
