import {Component} from '@angular/core';

@Component({
    selector: 'app-about-page',
    templateUrl: './about-page.component.html',
    styleUrls: ['./about-page.component.scss']
})
export class AboutPageComponent {
    texts: string[] = ['“This team are just the next level! I have been working with them for well over a year now and could not recommend them all highly enough. So refreshing in such a tough space.”',
        '“I really appreciate their professionalism, their commitment to making the working relationship successful and smooth, and their calm approach to managing tricky situations. In particular, Ruxi has been really great – pro-active and committed, whilst always listening to our priorities.”',
        '“Georgiana was really friendly and helped us when we wanted to see properties at times suitable for us. Great service and good property suggestions. Thank you so much!”',
        '“Very happy with the professional service received from Hamlet with the sale of my flat, particularly James (and Garn in his absence) from the Pinner team. They both kept me fully informed throughout the process, were responsive to phone calls and emails, and chased solicitors where necessary. Thank you!”',
        '“Helpful, reliable and professional service. Gave me peace of mind considering I live abroad, it was great to have them on my side!”'];
    currentIndex: number = 0;

    get currentText(): string {
        return this.texts[this.currentIndex];
    }

    changeText(index: number) {
        this.currentIndex = index;
    }

}
