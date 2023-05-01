import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

import {Property} from "../models/property";
import {PropertyService} from "../services/property.service";


@Component({
    selector: 'app-add-property-page',
    templateUrl: './add-property.component.html',
    styleUrls: ['./add-property.component.scss']
})
export class AddPropertyComponent {
    constructor(private service:PropertyService) {
    }

    form = new FormGroup({
        name: new FormControl(null, Validators.required),
        address: new FormControl(null, Validators.required),
        description: new FormControl(null, Validators.required),
        price: new FormControl(null, Validators.required),
        lot_area: new FormControl(null, Validators.required),
        // floorplan: new FormControl(null, Validators.required),
        // garden: new FormControl(null, Validators.required),
        isBuy: new FormControl(null, Validators.required),
        nb_bath: new FormControl(null, Validators.required),
        nb_bed: new FormControl(null, Validators.required),
        nb_garage: new FormControl(null, Validators.required),
        year_build: new FormControl(null, Validators.required),
        type: new FormControl(null, Validators.required),
         images: new FormControl(null, Validators.required)

    })

    newProperty: Property = {
        id: 80,
        address: '',
        description: '',
        epc: '',
        floorplan: '',
        garden: false,
        isBuy: false,
        lot_area: '',
        name: '',
        nb_bath: 0,
        nb_bed: 0,
        nb_garage: 0,
        price: '',
        year_build: 0,
        type: '',
        images: []
    }
//@ts-ignore
    image: File;



    onSubmit() {
        // if (this.form.invalid) {
        //    
        //     console.log("Ss",this.form.value)
        //     return
        // }
   
        // @ts-ignore
        this.newProperty.address=this.form.value.address;
        // @ts-ignore
        this.newProperty.description=this.form.value.description;
        // @ts-ignore
        this.newProperty.isBuy=this.form.value.isBuy;
        // @ts-ignore
        this.newProperty.lot_area=this.form.value.lot_area;
        // @ts-ignore
        this.newProperty.name=this.form.value.name;
        // @ts-ignore
        this.newProperty.nb_bath=this.form.value.nb_bath;
        // @ts-ignore
        this.newProperty.nb_bed=this.form.value.nb_bed;

        // @ts-ignore
        this.newProperty.nb_garage=this.form.value.nb_garage;
        // @ts-ignore
        this.newProperty.price=this.form.value.price;
        // @ts-ignore
        this.newProperty.year_build=this.form.value.year_build;
        // @ts-ignore
        this.newProperty.type=this.form.value.type;
        this.newProperty.images.push('https://media.vyomm.com/image/upload/if_w_gt_1080/w_1080/if_end/q_auto,f_auto/v1565802953/kv2hapyexstscczxiph1.jpg')
        this.service.createProperty(this.newProperty).subscribe(resp=>console.log(resp))

    }


    uploadFile($event: Event) {
        
    }
}































