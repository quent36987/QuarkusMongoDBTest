<script>
 export default {
    props:['user'],
 }
</script>

<template>
   <div class="user">
      <div class="user-picture">
         <img src="../assets/img/duck.jpg" alt="duck picture" width="100px" height="100px"> 
      </div>
      <div class="user-body">
         <p>{{ user.name }}</p>
      </div>
   </div>
</template>

<style>
.user{
   width: fit-content;
   border-radius: 40px;
   display: flex;
   flex-direction: row;
   margin: 1em;
   padding: 1em;
   box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 3px, rgba(0, 0, 0, 0.24) 0px 1px 2px;
}
.user-body{
   padding: 1em;
   border-top-right-radius: 40px;
   border-bottom-right-radius: 40px;
   text-align: left;
   box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 3px 0px;
}
</style>
