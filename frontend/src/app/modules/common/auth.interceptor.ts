import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (req.url.includes("/login") || req.url.includes("/register")) {
            return next.handle(req);
        }

        const token = sessionStorage.getItem("token"); // you probably want to store it in localStorage or something

        if (!token) {
            return next.handle(req);
        }

        const authRequest = req.clone({
            headers: req.headers.set('Authorization', `Bearer ${token}`)
        });

        return next.handle(authRequest);
    }

}