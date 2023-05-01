import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
    selector: 'app-dialogerror',
    templateUrl: './dialog-error.component.html',
    styleUrls: ['./dialog-error.component.scss']
})
export class DialogErrorComponent {
    message: string = 'fdfff'

    constructor(@Inject(MAT_DIALOG_DATA) public data: { message: string }) {
    }
}
