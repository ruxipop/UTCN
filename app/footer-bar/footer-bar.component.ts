import {Component} from '@angular/core';
import {faBolt} from "@fortawesome/free-solid-svg-icons";
import {
    faFacebook,
    faGoogle,
    faInstagram,
    faLinkedin,
    faTiktok,
    faTumblr,
    faTwitter,
    faYoutube
} from "@fortawesome/free-brands-svg-icons";

@Component({
    selector: 'app-footer-bar',
    templateUrl: './footer-bar.component.html',
    styleUrls: ['./footer-bar.component.scss']
})
export class FooterBarComponent {
    faBolt = faBolt;
    faFacebook = faFacebook;
    faTwitter = faTwitter;
    faGoogle = faGoogle;
    faInstagram = faInstagram;
    faLinkedin = faLinkedin;
    faYoutube = faYoutube;
    faTiktok = faTiktok;
    faTumblr = faTumblr;
}
