<script>
import PostsVue from './components/PostsVue.vue';
import UsersVue from './components/UsersVue.vue';
 
export default {
  data(){
    return{
      urls:{
        post: "http://localhost:8081/api",
        // social: import.meta.env.REPO_SOCIAL_API ? process.env.REPO_SOCIAL_API : "http://localhost:8082/api",
        // search: process.env.REPO_SEARCH_API ? process.env.REPO_SEARCH_API : "http://localhost:8083/api",
        // user_timeline: process.env.REPO_USER_TIMELINE_API ? process.env.REPO_USER_TIMELINE_API : "http://localhost:8084/api"
      },
      postsActive: true,
      usersActive: false,
    }
  },
  setup(){
    console.log(import.meta.env);
    console.log("urls: ", import.meta.env.VITE_REPO_POST_API);

  },
  components: {
      UsersVue,
      PostsVue
  },
  methods:{
    active(nb){
      if(nb == 0)
      {
        this.postsActive = true;
        this.usersActive = false;
      }
      else
      {
        this.postsActive = false;
        this.usersActive = true;
      }
  },
}
}
</script>

<template>
  <div id="welcome">
    <h1>Welcome to TinyX!</h1>
  </div>
  <main>
    <div class="pannel">
      <div class="pannel-header">
        <div @click="active(0)" :class="{active: postsActive}">Post</div>
        <div @click="active(1)" :class="{active: usersActive}">Users</div>
      </div>
      <div class="pannel-body">
        <PostsVue v-if="postsActive"  :urls="urls" ></PostsVue>
        <UsersVue v-if="usersActive"></UsersVue>
      </div>
    </div>
  </main>
</template>
<style>
#welcome{
  font-family: Arial, Helvetica, sans-serif;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 110px;
  width: 100%;
  background: rgb(171,210,250);
  background: radial-gradient(circle, rgba(171,210,250,1) 0%, rgba(255,255,255,1) 100%); 
  border-radius: 20px;
}
#welcome > h1{

  color: white;
  padding-left: 0.5em;
}
.pannel{
  margin-top: 0;
  padding-top: 0;
  font-family: Arial, Helvetica, sans-serif;
  width: 100%;
  height: fit-content;
}
.pannel-header{
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.pannel-header > div{
  border-radius: 20px;
  width: 100%;
  justify-content: center;
  text-align: center;
  padding: 1em;
  margin: 0.5em;
  font-size: larger;
  box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 3px, rgba(0, 0, 0, 0.24) 0px 1px 2px;
}
.pannel-header > div:hover{
  color: white;
  background-color: #7692FF;
}
.active{
  color: white;
  background-color: #7692FF;
}
</style>
