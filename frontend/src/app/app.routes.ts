import { Routes } from '@angular/router';
import { Home } from './pages/logged/home/home';
import { Movie } from './pages/logged/movie/movie';
import { Login } from './pages/not-logged/login/login';
import { SignIn } from './pages/not-logged/sign-in/sign-in';
import { authGuard } from './guard/auth.guard';
import { SearchResultsComponent } from './pages/logged/search-results-component/search-results-component';
import { Serie } from './pages/logged/serie/serie';
import { MainHome } from './pages/logged/main-home/main-home';
import { Category } from './pages/logged/category/category';
import { Streamings } from './pages/logged/streaming/streaming';
import { TopTen } from './pages/logged/top-ten/top-ten';
import { CreateSerie } from './pages/logged/create-serie/create-serie';
import { MovieId } from './pages/logged/movie-id/movie-id';
import { SerieId } from './pages/logged/serie-id/serie-id';
import { UserList } from './components/user-list/user-list';
import { Profile } from './pages/logged/profile/profile';
import { getProfile } from './pages/logged/get-profile/get-profile';
import { UserContent } from './pages/logged/user-content/user-content';
import { SearchProfile } from './pages/logged/search-profile/search-profile';
import { FindUserContent } from './pages/logged/find-user-content/find-user-content';


export const routes: Routes = [
    {
        path: "home",
        component: MainHome,
        canActivate: [authGuard]
    },
    {
        path: "create/movie",
        component: Movie,
        canActivate: [authGuard]
    },
    {
        path: "",
        component: Login
    },
    {
        path: "signIn",
        component: SignIn
    },
    {
        path: "search-results",
        component: SearchResultsComponent,
        canActivate: [authGuard]
    },
    {
        path: "serie",
        component: Serie,
        canActivate: [authGuard]
    },
    {
        path: "movies",
        component: Home,
        canActivate: [authGuard]
    },
    {
        path: "category/:id",
        component: Category,
        canActivate: [authGuard]
    },
    {
        path: "streaming/:id",
        component: Streamings,
        canActivate: [authGuard]
    },
    {
        path: "top10",
        component: TopTen,
        canActivate: [authGuard]
    },
    {
        path: "create/series",
        component: CreateSerie,
        canActivate: [authGuard]
    },
    {
        path: "movie/:id",
        component: MovieId,
        canActivate: [authGuard]
    },
    {
        path: "serie/:id",
        component: SerieId,
        canActivate: [authGuard]
    },
    {
        path: "save",
        component: UserList,
        canActivate: [authGuard]
    },
    {
        path:"profile",
        component:getProfile,
        canActivate:[authGuard]
    },
    {
        path:"edit/profile",
        component:Profile,
        canActivate:[authGuard]
    },
    {
        path:"user/content",
        component: UserContent,
        canActivate:[authGuard]
    },
    {
        path:"profile/user/:id",
        component:SearchProfile,
        canActivate:[authGuard]
    },
    {
        path:"content/user/:id",
        component: FindUserContent,
        canActivate: [authGuard]
    }
];
