import { inject } from "@angular/core"
import { AuthService } from "../service/auth.service"
import { Router } from "@angular/router";
import { map, of, take } from "rxjs";

export const authGuard = ()=>{
    const authService = inject(AuthService);
    const router = inject(Router);

    if(!authService.isAuthenticate){
        router.navigate(['']);
        return of(false);
    }

    return authService.validateToken().pipe(
        take(1),
        map(isValid=>{
            if(!isValid){
                localStorage.removeItem('token');
                router.navigate(['']);
                return of(false);
            }
            return true;
        })
    );

}