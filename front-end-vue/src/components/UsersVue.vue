<script>
import User from "./User.vue";
 
 export default {
    data(){
      return {
        allActive:true,
        myFollowersActive:false,
        checked : false,
        allUsersList : [{id:1, name:"Bonjour", nbPosts: 1}, {id:2, name:"Bonjour2", nbPosts: 1}],
        usersFollowed: [{id:1, name:"Bonjour", nbPosts: 1}],
        myFollowers: [{id:1, name:"Bonjour", nbPosts: 1}]
      }
    },
    methods:{
      active(nb){
        if(nb == 0)
        {
          this.allActive = true;
          this.myFollowersActive = false;
        }
        else
        {
          this.allActive = false;
          this.myFollowersActive = true;
        }
      }
    },
    components: {
        User,
    }
 }
</script>

<template>
  <div class="pannel-users">
    <div class="pannel-header-users">
      <div @click="active(0)" :class="{activeUser: allActive}">All</div>
      <div @click="active(1)" :class="{activeUser: myFollowersActive}">My followers</div>
    </div>
    <div v-if="allActive">
      <input type="checkbox" id="followers-only" name="I follow" v-model="checked" />
      <label for="followers-only">I follow</label>
    </div>
    <div class="pannel-body-users">
      <User v-if="!checked && allActive"  v-for="user in allUsersList" :user="user"></User>
      <User v-if="checked && allActive" v-for="user in usersFollowed" :user="user"></User>
      <User v-if="myFollowersActive" v-for="user in myFollowers" :user="user"></User>
    </div>
  </div>
</template>
<style>
.pannel-users{
  margin-top: 0;
  padding-top: 0;
  font-family: Arial, Helvetica, sans-serif;
  width: 100%;
  height: fit-content;
}
.pannel-header-users{
  width: 100%;
  display: flex;
  flex-direction: row;
  height: fit-content;
}
.pannel-header-users > div{
  border-radius: 20px;
  width: fit-content;
  justify-content: center;
  text-align: center;
  padding: 0.5em;
  margin: 0.25em;
  font-size: large;
  box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 3px, rgba(0, 0, 0, 0.24) 0px 1px 2px;
}
.pannel-header-users > div:hover{
  color: white;
  background-color: #1B2CC1;
}
.activeUser{
  color: white;
  background-color: #1B2CC1;
}
</style>
